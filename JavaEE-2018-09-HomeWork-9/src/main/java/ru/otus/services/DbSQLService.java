/*
 * DbSQLService.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import ru.otus.db.DBConf;
import ru.otus.db.PostgreSQLService;
import ru.otus.db.dao.ControllersOfClass;
import ru.otus.db.dao.DAOController;
import ru.otus.db.dao.jpa.DeptController;
import ru.otus.db.dao.jpa.EmpController;
import ru.otus.db.dao.jpa.GroupController;
import ru.otus.db.dao.jpa.UserController;
import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

public class DbSQLService extends PostgreSQLService implements DBConf, DbService
{
    private static final String SELECT_EMP_ENTITY = "SELECT e FROM EmpEntity e";
    private static final String SELECT_EMP_ENTITY_BY_ID = "SELECT e FROM EmpEntity e WHERE e.id = :id";
    private static final String SELECT_USER_ENTITY_BY_NAME = "SELECT e FROM UserEntity e WHERE e.login = :name";

    private static final String UPDATE_EMP_FIRST_NAME_BY_ID =
        "UPDATE EmpEntity e SET e.firstName = :name WHERE e.id = :id";
    private static final String UPDATE_EMP_SECOND_NAME_BY_ID =
        "UPDATE EmpEntity e SET e.secondName = :name WHERE e.id = :id";
    private static final String UPDATE_EMP_SUR_NAME_BY_ID = "UPDATE EmpEntity e SET e.surName = :name WHERE e.id = :id";

    private static final String DELETE_EMP_ENTITY_BY_ID = "DELETE FROM EmpEntity e WHERE e.id = :id";

    private static final String FIO_PREDICATE =
        "(e.firstName LIKE :name OR e.secondName LIKE :name OR e.surName LIKE :name)";
    private static final String JOB_PREDICATE = "e.job LIKE :job";
    private static final String CITY_PREDICATE = "e.city LIKE :city";
    private static final String AGE_PREDICATE = "e.age = :age";

    public DbSQLService()
    {
        super();
    }

    public DbSQLService(EntityManagerFactory entityManagerFactory)
    {
        super(entityManagerFactory);
    }

    @Override
    public void clearDb(ServletContext sc) throws ExceptionThrowable
    {
        super.clearDb(sc);
    }

    @Override
    public void importDb(ServletContext sc) throws ExceptionThrowable
    {
        super.importDb(sc);
    }

    @Override
    public void exportDb(ServletContext sc)
    {
        super.exportDb(sc);
    }

    public DeptController getDeptController()
    {
        return new DeptController(super.getEntityManagerFactory());
    }

    public UserController getUserController()
    {
        return new UserController(super.getEntityManagerFactory());
    }

    public GroupController getGroupController()
    {
        return new GroupController(super.getEntityManagerFactory());
    }

    public EmpController getEmpController()
    {
        return new EmpController(super.getEntityManagerFactory());
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E extends DataSet> DAOController<E, Long> getController(Class<E> c)
    {
        ControllersOfClass controller = ControllersOfClass.valueOf(c.getSimpleName());

        switch (controller) {
            case DeptEntity:
                return (DAOController<E, Long>) getDeptController();
            case UserEntity:
                return (DAOController<E, Long>) getUserController();
            case GroupEntity:
                return (DAOController<E, Long>) getGroupController();
            case EmpEntity:
                return (DAOController<E, Long>) getEmpController();
        }

        return null;
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


    //// LEGACY ///

    private <T extends DataSet> List<T> getEntities(String sql, Consumer<Query> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();
        try {
            transaction.begin();
            Query q = getEntityManager().createQuery(sql);
            if (null != c) c.accept(q);
            //noinspection unchecked
            List<T> result = new ArrayList<>(q.getResultList());
            transaction.commit();
            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private <T extends DataSet> List<T> getEntitiesViaClass(Class<T> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(c);
            Root<T> criteria = criteriaQuery.from(c);
            criteriaQuery.select(criteria);
            List<T> result = getEntityManager().createQuery(criteriaQuery).getResultList();
            transaction.commit();

            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private <T extends DataSet> T getEntity(String query, Consumer<Query> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            Query q = getEntityManager().createQuery(query);
            if (null != c) c.accept(q);
            //noinspection unchecked
            T result = (T) q.getSingleResult();
            transaction.commit();

            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    private <T extends DataSet> T getEntityById(String sql, long id)
    {
        return getEntity(sql, query -> query.setParameter("id", id));
    }

    private <T extends DataSet> T getEntityByName(String sql, String name)
    {
        return getEntity(sql, query -> query.setParameter("name", name));
    }

    @SuppressWarnings("Duplicates")
    private <T extends DataSet> T getEntityViaClassById(long id, Class<T> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(c);
            Root<T> criteria = query.from(c);
            query = query.select(criteria)
                .where(criteriaBuilder.equal(criteria.get("id"), id));
            T result = getEntityManager().createQuery(query).getSingleResult();
            transaction.commit();

            return result;
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
    }

    @Override
    public List<StatisticEntity> getAllStatisticElements() throws SQLException
    {
        return getEntitiesViaClass(StatisticEntity.class);
    }

    @SuppressWarnings("Duplicates")
    private <T extends DataSet> void mergeEntity(T entity)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            getEntityManager().merge(entity);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    private void queryUpdate(String query, Consumer<Query> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            Query q = getEntityManager().createQuery(query);
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

    @SuppressWarnings("Duplicates")
    private <T extends DataSet> void deleteEntityViaClassById(long id, Class<T> c)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
            CriteriaDelete<T> delete = criteriaBuilder.createCriteriaDelete(c);
            Root<T> criteria = delete.from(c);
            delete.where(criteriaBuilder.equal(criteria.get("id"), id));
            getEntityManager().createQuery(delete).executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
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

    public static Date localDateTimeToDate(LocalDateTime localDateTime)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(
            localDateTime.getYear(), localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth(),
            localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()
        );
        return calendar.getTime();
    }

    @Override
    public long insertIntoStatistic(StatisticEntity entity)
    {
        EntityTransaction transaction = getEntityManager().getTransaction();

        try {
            transaction.begin();
            UserEntity user = entity.getUser();
            if (null == user) {
                user = new UserEntity();
                user.setId(-1L);
            }

            StoredProcedureQuery proc = getEntityManager().createNamedStoredProcedureQuery("insert_statistic");
            proc.setParameter("name_marker", entity.getNameMarker());
            proc.setParameter("jsp_page_name", entity.getJspPageName());
            proc.setParameter("ip_address", entity.getIpAddress());
            proc.setParameter("user_agent", entity.getUserAgent());
            proc.setParameter("client_time", localDateTimeToDate(entity.getClientTime()), TemporalType.TIMESTAMP);
            proc.setParameter("server_time", localDateTimeToDate(entity.getServerTime()), TemporalType.TIMESTAMP);
            proc.setParameter("session_id", entity.getSessionId());
            proc.setParameter("user_id", user.getId());
            proc.setParameter("prev_id", entity.getPreviousId());

            BigInteger result = (BigInteger) proc.getSingleResult();
            transaction.commit();
            return result.longValue();
        }
        catch (Exception e) {
            transaction.rollback();
            throw e;
        }
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
    public void deleteEmpEntityById(long id)
    {
        queryUpdateById(DELETE_EMP_ENTITY_BY_ID, id);
    }

    @Override
    public <T extends DataSet> void deleteEntityById(long id, Class<T> c)
    {
        deleteEntityViaClassById(id, c);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
