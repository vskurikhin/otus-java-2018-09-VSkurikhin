package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.db.ImportSmallEntities;
import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DbJPAPostgreSQLService implements DbService
{
    public static final String DEPT_ENTITIES_XML_DATA_FILE_LOCATION = "DeptEntitiesXMLDataFileLocation";
    public static final String USER_ENTITIES_XML_DATA_FILE_LOCATION = "UserEntitiesXMLDataFileLocation";
    public static final String EMP_ENTITIES_XML_DATA_FILE_LOCATION = "EmpEntitiesXMLDataFileLocation";
    public static final String GROUP_ENTITIES_XML_DATA_FILE_LOCATION = "UsersgroupEntitiesXMLDataFileLocation";

    private static final String SELECT_EMP_ENTITY = "SELECT e FROM EmpEntity e";
    private static final String SELECT_EMP_ENTITY_BY_ID = "SELECT e FROM EmpEntity e WHERE e.id = :id";

    private static final String FIO_PREDICATE =
            "(e.firstName LIKE :name OR e.secondName LIKE :name OR e.surName LIKE :name)";
    private static final String JOB_PREDICATE = "e.job LIKE :job";
    private static final String CITY_PREDICATE = "e.city LIKE :city";
    private static final String AGE_PREDICATE = "e.age = :age";

    private static final String UPDATE_EMP_FIRST_NAME_BY_ID =
            "UPDATE EmpEntity e SET e.firstName = :name WHERE e.id = :id";
    private static final String UPDATE_EMP_SECOND_NAME_BY_ID =
            "UPDATE EmpEntity e SET e.secondName = :name WHERE e.id = :id";
    private static final String UPDATE_EMP_SUR_NAME_BY_ID = "UPDATE EmpEntity e SET e.surName = :name WHERE e.id = :id";

    private static final String DELETE_EMP_ENTITY_BY_ID = "DELETE FROM EmpEntity e WHERE e.id = :id";
    private static final String DELETE_EMP_REGISTRY_CASCADE = "DELETE FROM emp_registry CASCADE";
    private static final String DELETE_DEP_DIRECTORY_CASCADE = "DELETE FROM dep_directory CASCADE";
    private static final String DELETE_USERS_CASCADE = "DELETE FROM users CASCADE";
    private static final String DELETE_USER_GROUPS_CASCADE = "DELETE FROM user_groups CASCADE";
    private static final String ALTER_SEQ_RESTART_WITH_1 = "ALTER SEQUENCE hibernate_sequence RESTART WITH 1";

    private EntityManager em;

    public DbJPAPostgreSQLService(EntityManager em)
    {
        this.em = em;
    }

    @Override
    public void clearDb(ServletContext sc) throws Exception
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.createNativeQuery(DELETE_EMP_REGISTRY_CASCADE).executeUpdate();
            em.createNativeQuery(DELETE_DEP_DIRECTORY_CASCADE).executeUpdate();
            em.createNativeQuery(DELETE_USERS_CASCADE).executeUpdate();
            em.createNativeQuery(DELETE_USER_GROUPS_CASCADE).executeUpdate();
            em.createNativeQuery(ALTER_SEQ_RESTART_WITH_1).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void importDb(ServletContext sc) throws Exception
    {
        String deptEntitiesXMLPath = sc.getInitParameter(DEPT_ENTITIES_XML_DATA_FILE_LOCATION);
        ImportSmallEntities<DeptEntitiesList> import1 = new ImportSmallEntities<>(sc, deptEntitiesXMLPath);
        import1.saveEntities(em);

        String userEntitiesXMLPath = sc.getInitParameter(USER_ENTITIES_XML_DATA_FILE_LOCATION);
        ImportSmallEntities<UserEntitiesList> import2 = new ImportSmallEntities<>(sc, userEntitiesXMLPath);
        import2.saveEntities(em);

        String empEntitiesXMLPath = sc.getInitParameter(EMP_ENTITIES_XML_DATA_FILE_LOCATION);
        ImportSmallEntities<EmpEntitiesList> import3 = new ImportSmallEntities<>(sc, empEntitiesXMLPath);
        import3.saveEntities(em);

        String groupEntitiesXMLPath = sc.getInitParameter(GROUP_ENTITIES_XML_DATA_FILE_LOCATION);
        ImportSmallEntities<GroupEntitiesList> import4 = new ImportSmallEntities<>(sc, groupEntitiesXMLPath);
        import4.saveEntities(em);
    }

    @Override
    public void exportDb(ServletContext sc)
    {
        // TODO
    }

    private List<EmpEntity> getEmpEntities(String sql, Consumer<Query> c)
    {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Query q = em.createQuery(sql);
            if (null != c) c.accept(q);
            //noinspection unchecked
            List<EmpEntity> result = new ArrayList<>(q.getResultList());
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<EmpEntity> getEmpEntities()
    {
        return getEmpEntities(SELECT_EMP_ENTITY, null);
    }

    public <T extends DataSet> T getSingleResultById(String query, long id)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query q = em.createQuery(query);
            q.setParameter("id", id);
            //noinspection unchecked
            T result = (T) q.getSingleResult();
            transaction.commit();
            return result;
        } catch (Exception e){
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public EmpEntity getEmpEntityById(long id)
    {
        return getSingleResultById(SELECT_EMP_ENTITY_BY_ID, id);
    }

    @Override
    public void saveEmpEntity(EmpEntity entity)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.merge(entity);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    private void queryUpdate(String query, long id, String value)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query q = em.createQuery(query);
            q.setParameter("id", id);
            q.setParameter("name", value);
            q.executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e); // TODO
        }
    }

    @Override
    public void updateFirstNameInEmpEntityById(long id, String firstName)
    {
        queryUpdate(UPDATE_EMP_FIRST_NAME_BY_ID, id, firstName);
    }

    @Override
    public void updateSecondNameInEmpEntityById(long id, String secondName)
    {
        queryUpdate(UPDATE_EMP_SECOND_NAME_BY_ID, id, secondName);
    }

    @Override
    public void updateSurNameInEmpEntityById(long id, String surName)
    {
        queryUpdate(UPDATE_EMP_SUR_NAME_BY_ID, id, surName);
    }

    @Override
    public void deleteEmpEntityById(long id)
    {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Query q = em.createQuery(DELETE_EMP_ENTITY_BY_ID);
            q.setParameter("id", id);
            q.executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e); // TODO
        }
    }

    private boolean isCorrectAttr(String key, Map<String, Object> attrs)
    {
        return attrs.containsKey(key) && attrs.get(key) != null;
    }

    @Override
    public List<EmpEntity> searchEmpEntity(Map<String, Object> attrs)
    {
        String sql = SELECT_EMP_ENTITY;
        String sqlTerm = " WHERE ";
        String sqlTermAnd = " AND ";

        if (isCorrectAttr("name", attrs)) {
            sql += sqlTerm;
            sql += FIO_PREDICATE;
            sqlTerm = sqlTermAnd;
        }
        if (isCorrectAttr("job", attrs)) {
            sql += sqlTerm;
            sql += JOB_PREDICATE;
            sqlTerm = sqlTermAnd;
        }
        if (isCorrectAttr("city", attrs)) {
            sql += sqlTerm;
            sql += CITY_PREDICATE;
            sqlTerm = sqlTermAnd;
        }
        if (isCorrectAttr("age", attrs)) {
            sql += sqlTerm;
            sql += AGE_PREDICATE;
        }

        return getEmpEntities(sql, q -> attrs.forEach(q::setParameter));
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
