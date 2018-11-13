package ru.otus.db;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;

import static ru.otus.utils.IO.readInputStream;

public class ImporterSmallXML<T extends EntitiesList>
{
    private String xmlObjects;
    private JAXBContext jc;

    public ImporterSmallXML(ServletContext sc, String filename) throws IOException, JAXBException
    {
        jc = JAXBContext.newInstance(
                StatisticEntitiesList.class, EmpEntitiesList.class, UserEntitiesList.class,
                DeptEntitiesList.class, GroupEntitiesList.class,
                StatisticEntity.class, DeptEntity.class, EmpEntity.class, UserEntity.class,
                GroupEntity.class
        );

        try(InputStream is = sc.getResourceAsStream(filename)) {
            xmlObjects = readInputStream(is, "UTF-8");
        } catch (IOException e) {
            throw e;
        }
    }

    public <E extends DataSet> void saveEntities(EntityManager em)
    throws JAXBException, IOException
    {
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        //noinspection unchecked
        T entities = (T) unmarshaller.unmarshal(new StringReader(xmlObjects));

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Object e : entities.asList()) {
            //noinspection unchecked
            E entity = (E) e;
            em.merge(entity);
        }
        transaction.commit();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
