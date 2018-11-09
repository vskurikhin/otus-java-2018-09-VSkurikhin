package ru.otus.db;

import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

public class ImportEntities <T extends EntitiesList>
{
    private T entities;

    public ImportEntities(ServletContext sc, String filename) throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(
                EmpEntitiesList.class, UserEntitiesList.class, DeptEntitiesList.class, GroupEntitiesList.class,
                DeptEntity.class, EmpEntity.class, UserEntity.class, GroupEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();
        InputStream is = sc.getResourceAsStream(filename);

        //noinspection unchecked
        entities = (T) u.unmarshal(is);
    }

    public  <E extends DataSet>  void saveEntities(EntityManager em) throws Exception
    {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            for (Object e : entities.asList()) {
                //noinspection unchecked
                E entity = (E) e;
                em.merge(entity);
            }
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
