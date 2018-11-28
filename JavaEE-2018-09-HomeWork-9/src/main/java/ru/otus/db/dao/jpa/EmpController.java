/*
 * Created by VSkurikhin 28.11.18 23:24.
 * EmpController.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.exeptions.ExceptionThrowable;
import ru.otus.models.EmpEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class EmpController extends AbstractController <EmpEntity, Long>
{
    public EmpController(EntityManager entityManager)
    {
        super(entityManager);
    }

    @Override
    public List<EmpEntity> getAll() throws ExceptionThrowable
    {
        return getAll(EmpEntity.class);
    }

    @Override
    public EmpEntity getEntityById(Long id) throws ExceptionThrowable
    {
        return getEntityViaClassById(id, EmpEntity.class);
    }

    public EmpEntity getEntityByTitle(String title) throws ExceptionThrowable
    {
        return getEntityViaClassByName("title", title, EmpEntity.class);
    }

    @Override
    public EmpEntity update(EmpEntity entity) throws ExceptionThrowable
    {
        return mergeEntity(entity);
    }

    @Override
    public boolean delete(Long id) throws ExceptionThrowable
    {
        return deleteEntityViaClassById(id, EmpEntity.class);
    }

    @Override
    public boolean create(EmpEntity entity) throws ExceptionThrowable
    {
        return persistEntity(entity);
    }

    public long getMaxSalary() throws ExceptionThrowable
    {
        return getMaxLong("salary", EmpEntity.class);
    }

    public double getAvgSalary() throws ExceptionThrowable
    {
        return getAvgDouble("salary", EmpEntity.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
