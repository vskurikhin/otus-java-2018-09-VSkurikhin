/*
 * EmpController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.db.dao.EmpDAO;
import ru.otus.models.entities.EmpEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.*;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Stateless
@TransactionAttribute(SUPPORTS)
public class EmpController extends AbstractController<EmpEntity, Long> implements EmpDAO
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    void setEntityManager(EntityManager em)
    {
        this.em = em;
    }

    @Override
    protected Class<EmpEntity> getTypeFirstParameterClass()
    {
        return EmpEntity.class;
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public List<EmpEntity> findAll()
    {
        return findAll(EmpEntity.class);
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public EmpEntity findEntityById(Long id)
    {
        return findEntityViaClassById(id, EmpEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public EmpEntity update(EmpEntity entity)
    {
        return mergeEntity(entity);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        return deleteEntityViaClassById(id, EmpEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean create(EmpEntity entity)
    {
        return mergeEntity(entity) != null;
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public void updateFirstName(Long id, String firstName)
    {
        updateEntity(id, entity -> entity.setFirstName(firstName));
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public void updateSecondName(Long id, String secondName)
    {
        updateEntity(id, entity -> entity.setSecondName(secondName));
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public void updateSurName(Long id, String surName)
    {
        updateEntity(id, entity -> entity.setSurName(surName));
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public long findMaxSalary()
    {
        return findMaxLong("salary", EmpEntity.class);
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public double calcAvgSalary()
    {
        return caclAvgDouble("salary", EmpEntity.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
