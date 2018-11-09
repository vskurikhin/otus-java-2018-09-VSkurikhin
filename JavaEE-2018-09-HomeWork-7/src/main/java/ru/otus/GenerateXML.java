package ru.otus;

/*
 * Created by VSkurikhin at autumn 2018.
 */
/**
 * mvn clean compile dependency:copy-dependencies
 * generateXML.sh or generateXML.bat
 */

import ru.otus.adapters.function.CreateDeptEntity;
import ru.otus.adapters.function.CreateEmpEntity;
import ru.otus.adapters.function.CreateGroupEntity;
import ru.otus.adapters.function.CreateUserEntity;
import ru.otus.models.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.function.Function;

public class GenerateXML
{
    private String filename;
    private Function<String[], ? extends DataSet> function;
    private Scanner inputStream;

    private <T extends EntitiesList> T readInputStream(T list)
    {
        while (inputStream.hasNextLine()) {
            String line = inputStream.nextLine();
            String[] fields = removeFirstAndLastApostrophes(line.split(","));

            if ( fields.length > 0 && "id".equals(fields[0])) {
                continue;
            }

            if (fields.length >= 2) {
                //noinspection unchecked
                list.add(function.apply(fields));
            }
            else {
                System.err.println("Invalid record: " + line);
            }
        }
        return list;
    }

    private String[] removeFirstAndLastApostrophes(String[] array)
    {
        for (int i = 0; i < array.length; ++i) {
            if (array[i].length() > 1 && array[i].charAt(0) == '\'') {
                int lastIndex = array[i].length() - 1;

                if (array[i].charAt(lastIndex) == '\'') {
                    array[i] = array[i].substring(1, lastIndex);
                }
            }
        }
        return array;
    }

    private <T extends EntitiesList> void marshalEntitiesList(T result)
    {
        try {
            JAXBContext context = JAXBContext.newInstance(result.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            File file = Paths.get(filename).toFile();
            m.marshal(result, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private GenerateXML(String filename, String entityType) {
        this.inputStream = new Scanner(System.in);
        this.filename = filename;
        switch (entityType.toLowerCase()) {
            case "deptentity" :
                this.function = new CreateDeptEntity();
                marshalEntitiesList(readInputStream(new DeptEntitiesList()));
                break;
            case "empentity" :
                this.function = new CreateEmpEntity();
                marshalEntitiesList(readInputStream(new EmpEntitiesList()));
                break;
            case "usersgroupentity" :
                this.function = new CreateGroupEntity();
                marshalEntitiesList(readInputStream(new GroupEntitiesList()));
                break;
            case "userentity" :
                this.function = new CreateUserEntity();
                marshalEntitiesList(readInputStream(new UserEntitiesList()));
                break;
            default :
                throw new IllegalArgumentException("Second argument is illegal in the command line!");
        }
    }

    public static void main(String[] args) throws Exception
    {
        if (args.length != 2) {
            System.out.println("For work this loader need one argument with table name.");
        } else if (args[0] == null || args[1] == null) {
            System.out.println("The args is null!");
        } else {
            new GenerateXML(args[0], args[1]);
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
