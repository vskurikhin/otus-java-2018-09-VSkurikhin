package ru.otus.db;

import ru.otus.cfg.CFGProperties;
import ru.otus.utils.ResourceAsStream;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static ru.otus.db.DBConf.*;

@Singleton
@Startup
public class InitializePostgreSQL
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @EJB
    CFGProperties cfgProperties;

    protected EntityManager getEntityManager()
    {
        return em;
    }

    void setEntityManager(EntityManager emf)
    {
        this.em = em;
    }

    private static final String DELETE_FROM = "DELETE FROM ";
    private static final String CASCADE = " CASCADE";
    private static final String ALTER_SEQUENCE = "ALTER SEQUENCE ";
    private static final String RESTART_WITH_1 = " RESTART WITH 1";

    @PostConstruct
    public void importDb()
    {
        EntityManager em = getEntityManager();

        try {
            for (String table : TABLES) {
                String filename = cfgProperties.getProperty(FILE_LOCATIONS.get(table));
                if (null != filename) {
                    ResourceAsStream resStream = new ResourceAsStream(filename);
                    DBConf.createImporterXML(resStream).saveEntities(em);
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom Exception
        }
    }

    @PreDestroy
    public void clearDb()
    {
        try {
            for (String table : ORDER_OF_DELETE_TABLES) {
                em.createNativeQuery(DELETE_FROM + table + CASCADE).executeUpdate();
            }
            for (String seq : ORDER_OF_RESTART_SEQUENCE) {
                em.createNativeQuery(ALTER_SEQUENCE + seq + RESTART_WITH_1).executeUpdate();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom Exception
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
