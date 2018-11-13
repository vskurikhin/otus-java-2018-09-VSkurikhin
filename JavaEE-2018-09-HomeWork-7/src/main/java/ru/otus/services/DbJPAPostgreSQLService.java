package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.db.ImporterSmallXML;
import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DbJPAPostgreSQLService implements DbService
{
    public static final String STATISTIC_ENTITIES_XML_DATA_FILE_LOCATION = "StatisticEntitiesXMLDataFileLocation";
    public static final String DEPT_ENTITIES_XML_DATA_FILE_LOCATION = "DeptEntitiesXMLDataFileLocation";
    public static final String USER_ENTITIES_XML_DATA_FILE_LOCATION = "UserEntitiesXMLDataFileLocation";
    public static final String EMP_ENTITIES_XML_DATA_FILE_LOCATION = "EmpEntitiesXMLDataFileLocation";
    public static final String GROUP_ENTITIES_XML_DATA_FILE_LOCATION = "UsersgroupEntitiesXMLDataFileLocation";

    private static final String SELECT_EMP_ENTITY = "SELECT e FROM EmpEntity e";
    private static final String SELECT_EMP_ENTITY_BY_ID = "SELECT e FROM EmpEntity e WHERE e.id = :id";
    private static final String SELECT_USER_ENTITY_BY_NAME =  "SELECT e FROM UserEntity e WHERE e.login = :name";;

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
    private static final String DELETE_STATISTIC_REGISTRY_CASCADE = "DELETE FROM statistic CASCADE";
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
            em.createNativeQuery(DELETE_STATISTIC_REGISTRY_CASCADE).executeUpdate();
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
        String statisticEntitiesXMLPath = sc.getInitParameter(STATISTIC_ENTITIES_XML_DATA_FILE_LOCATION);
        ImporterSmallXML<StatisticEntitiesList> import0 = new ImporterSmallXML<>(sc, statisticEntitiesXMLPath);
        import0.saveEntities(em);

        String deptEntitiesXMLPath = sc.getInitParameter(DEPT_ENTITIES_XML_DATA_FILE_LOCATION);
        ImporterSmallXML<DeptEntitiesList> import1 = new ImporterSmallXML<>(sc, deptEntitiesXMLPath);
        import1.saveEntities(em);

        String userEntitiesXMLPath = sc.getInitParameter(USER_ENTITIES_XML_DATA_FILE_LOCATION);
        ImporterSmallXML<UserEntitiesList> import2 = new ImporterSmallXML<>(sc, userEntitiesXMLPath);
        import2.saveEntities(em);

        String empEntitiesXMLPath = sc.getInitParameter(EMP_ENTITIES_XML_DATA_FILE_LOCATION);
        ImporterSmallXML<EmpEntitiesList> import3 = new ImporterSmallXML<>(sc, empEntitiesXMLPath);
        import3.saveEntities(em);

        String groupEntitiesXMLPath = sc.getInitParameter(GROUP_ENTITIES_XML_DATA_FILE_LOCATION);
        ImporterSmallXML<GroupEntitiesList> import4 = new ImporterSmallXML<>(sc, groupEntitiesXMLPath);
        import4.saveEntities(em);
    }

    @Override
    public void exportDb(ServletContext sc)
    {
        // TODO
    }

    private <T extends DataSet> List<T> getEntities(String sql, Consumer<Query> c)
    {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Query q = em.createQuery(sql);
            if (null != c) c.accept(q);
            //noinspection unchecked
            List<T> result = new ArrayList<>(q.getResultList());
            transaction.commit();
            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private <T extends DataSet> List<T> getEntitiesViaClass(Class<T> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
            Root<T> criteria = criteriaQuery.from(c);
            criteriaQuery.select(criteria);
            List<T> result = em.createQuery(criteriaQuery).getResultList();
            transaction.commit();

            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private  <T extends DataSet> T getEntity(String query, Consumer<Query> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query q = em.createQuery(query);
            if (null != c) c.accept(q);
            //noinspection unchecked
            T result = (T) q.getSingleResult();
            transaction.commit();
            return result;
        } catch (Exception e){
            transaction.rollback();
            throw e;
        }
    }

    private  <T extends DataSet> T getEntityById(String sql, long id)
    {
        return getEntity(sql, query -> query.setParameter("id", id));
    }

    private  <T extends DataSet> T getEntityByName(String sql, String name)
    {
        return getEntity(sql, query -> query.setParameter("name", name));
    }

    private <T extends DataSet> T getEntityViaClassById(long id, Class<T> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(c);
            Root<T> criteria = query.from(c);
            query = query.select(criteria)
                    .where(criteriaBuilder.equal(criteria.get("id"), id));
            T result = em.createQuery(query).getSingleResult();
            transaction.commit();

            return result;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private  <T extends DataSet> void mergeEntity(T entity)
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

    private void queryUpdate(String query, Consumer<Query> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query q = em.createQuery(query);
            if (null != c) c.accept(q);
            q.executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e); // TODO
        }
    }

    private void queryUpdateById(String query, long id)
    {
        queryUpdate(query, q -> q.setParameter("id", id));
    }

    private void queryUpdateVariableNameById(String query, long id, String value)
    {
        queryUpdate(query, q -> {
            q.setParameter("id", id);
            q.setParameter("name", value);
        });
    }

    private <T extends DataSet> void deleteEntityViaClassById(long id, Class<T> c)
    {
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaDelete<T> delete = criteriaBuilder.createCriteriaDelete(c);
            Root<T> criteria = delete.from(c);
            delete.where(criteriaBuilder.equal(criteria.get("id"), id));
            em.createQuery(delete).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private boolean isCorrectAttr(String key, Map<String, Object> attrs)
    {
        return attrs.containsKey(key) && attrs.get(key) != null;
    }

    @Override
    public List<EmpEntity> getEmpEntities()
    {
        return getEntities(SELECT_EMP_ENTITY, null);
    }

    @Override
    public <T extends DataSet> List<T> getEntities(Class<T> c)
    {
        return getEntitiesViaClass(c);
    }

    @Override
    public EmpEntity getEmpEntityById(long id)
    {
        try {
            return getEntityById(SELECT_EMP_ENTITY_BY_ID, id);
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public UserEntity getUserEntityByName(String name)
    {
        try {
            return getEntityByName(SELECT_USER_ENTITY_BY_NAME, name);
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public <T extends DataSet> T getEntityById(long id, Class<T> c)
    {
        try {
            return getEntityViaClassById(id, c);
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public <T extends DataSet> void saveEntity(T entity)
    {
        mergeEntity(entity);
    }

    @Override
    public void updateFirstNameInEmpEntityById(long id, String firstName)
    {
        queryUpdateVariableNameById(UPDATE_EMP_FIRST_NAME_BY_ID, id, firstName);
    }

    @Override
    public void updateSecondNameInEmpEntityById(long id, String secondName)
    {
        queryUpdateVariableNameById(UPDATE_EMP_SECOND_NAME_BY_ID, id, secondName);
    }

    @Override
    public void updateSurNameInEmpEntityById(long id, String surName)
    {
        queryUpdateVariableNameById(UPDATE_EMP_SUR_NAME_BY_ID, id, surName);
    }

    @Override
    public void deleteEmpEntityById(long id)
    {
        queryUpdateById(DELETE_EMP_ENTITY_BY_ID, id);
    }

    @Override
    public <T extends DataSet> void deleteEntityById(long id, Class<T> c)
    {
        deleteEntityViaClassById(id, c);
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

        return getEntities(sql, q -> attrs.forEach(q::setParameter));
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
