package ru.otus.services;

import ru.otus.db.ImportEntities;
import ru.otus.models.DeptEntitiesList;
import ru.otus.models.EmpEntitiesList;
import ru.otus.models.GroupEntitiesList;
import ru.otus.models.UserEntitiesList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContext;

public class DbJPAService implements DbService
{
    private final String FILENAME_DEPT_ENTITIES = "/WEB-INF/classes/DeptEntities.xml";
    private final String FILENAME_USER_ENTITIES = "/WEB-INF/classes/UserEntities.xml";
    private final String FILENAME_EMP_ENTITIES = "/WEB-INF/classes/EmpEntities.xml";
    private final String FILENAME_GROUP_ENTITIES = "/WEB-INF/classes/UsersgroupEntities.xml";
    private EntityManager em;

    public DbJPAService(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public void clearDb(ServletContext sc) throws Exception
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.createNativeQuery("DELETE FROM emp_registry CASCADE").executeUpdate();
            em.createNativeQuery("DELETE FROM dep_directory CASCADE").executeUpdate();
            em.createNativeQuery("DELETE FROM users CASCADE").executeUpdate();
            em.createNativeQuery("DELETE FROM user_groups CASCADE").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE hibernate_sequence RESTART WITH 1").executeUpdate();
            transaction.commit();
        } finally {
            transaction.rollback();
        }
    }

    @Override
    public void importDb(ServletContext sc) throws Exception
    {
        ImportEntities<DeptEntitiesList> importEntities1 = new ImportEntities<>(sc, FILENAME_DEPT_ENTITIES);
        importEntities1.saveEntities(em);
        ImportEntities<UserEntitiesList> importEntities2 = new ImportEntities<>(sc, FILENAME_USER_ENTITIES);
        importEntities2.saveEntities(em);
        ImportEntities<EmpEntitiesList> importEntities3 = new ImportEntities<>(sc, FILENAME_EMP_ENTITIES);
        importEntities3.saveEntities(em);
        ImportEntities<GroupEntitiesList> importEntities4 = new ImportEntities<>(sc, FILENAME_GROUP_ENTITIES);
        importEntities4.saveEntities(em);
    }

    @Override
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
