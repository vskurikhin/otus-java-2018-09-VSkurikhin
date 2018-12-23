/*
 * AbstractController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.db.dao.DAOController;
import ru.otus.models.DataSet;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.function.Consumer;

import static ru.otus.shared.Constants.DB.F_ID;

public abstract class AbstractController<E extends DataSet, K> implements DAOController<E, K>
{
    private Class<E> classE;

    protected abstract EntityManager getEntityManager();

    protected abstract Class<E> getTypeFirstParameterClass();

    public E findById(EntityManager entityManager, long key)
    {
        if (null == classE) {
            classE = getTypeFirstParameterClass();
        }

        return entityManager.find(classE, key);
    }

    public E findById(long key)
    {
        EntityManager em = getEntityManager();

        try {
            return findById(em, key);
        }
        catch (NoResultException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected List<E> findAll(Class<E> clazz)
    {
        List<E> result = null;
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<E> criteriaQuery = criteriaBuilder.createQuery(clazz);
            Root<E> criteria = criteriaQuery.from(clazz);
            criteriaQuery.select(criteria);
            result = em.createQuery(criteriaQuery).getResultList();

            return result;
        }
        catch (Throwable e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected List<E> findEntitiesViaClassByKey(String keyName, K key, Class<E> clazz)
    {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<E> query = criteriaBuilder.createQuery(clazz);
            Root<E> criteria = query.from(clazz);
            query = query.select(criteria)
                    .where(criteriaBuilder.equal(criteria.get(keyName), key));

            return em.createQuery(query).getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected <V> TypedQuery<E> queryViaClassByFieldValue(String fieldName, V value, Class<E> clazz)
    {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<E> query = criteriaBuilder.createQuery(clazz);
            Root<E> criteria = query.from(clazz);
            query = query.select(criteria)
                .where(criteriaBuilder.equal(criteria.get(fieldName), value));

            return em.createQuery(query);
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected <V> E findEntityViaClassByFieldValue(String fieldName, V value, Class<E> clazz)
    {
        try {
            return queryViaClassByFieldValue(fieldName, value, clazz).getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    protected  <V> List<E> findEntitiesViaClassByFieldValue(String fieldName, V value, Class<E> clazz)
    {
        try {
            return queryViaClassByFieldValue(fieldName, value, clazz).getResultList();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    protected <V> E findEntityViaClassByName(String fieldName, String valueName, Class<E> clazz)
    {
        return findEntityViaClassByFieldValue(fieldName, valueName, clazz);
    }

    protected E findEntityViaClassById(K id, Class<E> clazz)
    {
        return findEntityViaClassByFieldValue(F_ID, id, clazz);
    }

    protected E mergeEntity(E entity)
    {
        EntityManager em = getEntityManager();

        try {
            return em.merge(entity);
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected boolean deleteEntityViaClassById(K id, Class<E> c)
    {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaDelete<E> delete = criteriaBuilder.createCriteriaDelete(c);
            Root<E> criteria = delete.from(c);
            delete.where(criteriaBuilder.equal(criteria.get("id"), id));
            int count = em.createQuery(delete).executeUpdate();

            return count > 0;
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected E updateEntity(K key, Consumer<E> consumer)
    {
        if (null == classE) {
            classE = getTypeFirstParameterClass();
        }

        EntityManager em = getEntityManager();

        try {
            E entity = em.find(classE, key);
            consumer.accept(entity);

            return em.merge(entity);
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected <R> R callStoredProcedureSingleResult(String name, Consumer<StoredProcedureQuery> c)
    {
        EntityManager em = getEntityManager();

        try {
            StoredProcedureQuery proc = em.createNamedStoredProcedureQuery(name);
            c.accept(proc);
            //noinspection unchecked
            R result = (R) proc.getSingleResult();

            return result;
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected boolean persistEntity(E entity)
    {
        EntityManager em = getEntityManager();

        try {
            em.persist(entity);

            return true;
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected long findMaxLong(String fieldName, Class<E> clazz)
    {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Long> query = criteriaBuilder.createQuery(Long.class);
            Root<E> criteria = query.from(clazz);

            query.select(criteriaBuilder.max(criteria.get(fieldName)));

            long maxValue = 0L;

            try {
                maxValue = em.createQuery(query).getSingleResult();
            }
            catch (NoResultException nre) {
                maxValue = -1L;
            }

            return maxValue;
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }

    protected double caclAvgDouble(String fieldName, Class<E> clazz)
    {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Double> query = criteriaBuilder.createQuery(Double.class);
            Root<E> criteria = query.from(clazz);

            query.select(criteriaBuilder.avg(criteria.get(fieldName)));

            double avgValue = 0;

            try {
                avgValue = em.createQuery(query).getSingleResult();
            }
            catch (NoResultException nre) {
                avgValue = -1;
            }

            return avgValue;
        }
        catch (Exception e) {
            throw new RuntimeException(e); // TODO Custom RuntimeException
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
