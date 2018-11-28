/*
 * Created by VSkurikhin 28.11.18 21:27.
 * PostgreSQLService.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletContext;
import java.io.Closeable;

public class PostgreSQLService implements DBConf, Closeable
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    // private static final EntityManagerFactory emf =
    // Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish

    private EntityManager em;

    private static final String ALTER_SEQ_RESTART_WITH_1 = "ALTER SEQUENCE emp_id_seq RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_2 = "ALTER SEQUENCE hibernate_sequence RESTART WITH 1";
    private static final String ALTER_SEQ_RESTART_WITH_3 = "ALTER SEQUENCE statistic_id_seq RESTART WITH 1";

    public PostgreSQLService()
    {
        emf.createEntityManager();
    }

    public PostgreSQLService(EntityManager em)
    {
        this.em = em;
    }

    public EntityManager getEM()
    {
        return em;
    }

    public void clearDb(ServletContext sc) throws Exception
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            for (String table : ORDER_OF_DELETE_TABLES) {
                em.createNativeQuery("DELETE FROM " + table + " CASCADE").executeUpdate();
            }
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_1).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_2).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_3).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    public void importDb(ServletContext sc) throws Exception
    {
        for (String table : TABLES) {
            DBConf.createImporterXML(sc, FILE_LOCATIONS.get(table)).saveEntities(em);
        }
    }

    public void exportDb(ServletContext sc)
    {
        // TODO
    }

    @Override
    public void close()
    {
        em.close();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
