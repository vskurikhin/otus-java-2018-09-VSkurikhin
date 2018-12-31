/*
 * TestController.java
 * This file was last modified at 29.11.18 21:47 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.models.TestEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.SynchronizationType;
import java.util.List;

public class TestController extends AbstractController <TestEntity, Long>
{
    private EntityManagerFactory emf;

    @Override
    protected EntityManager getEntityManager()
    {
        if (null == emf) {
            emf = Persistence.createEntityManagerFactory("test");
        }

        return emf.createEntityManager(SynchronizationType.UNSYNCHRONIZED);
    }

    void setEntityManager(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    @Override
    protected Class<TestEntity> getTypeFirstParameterClass()
    {
        return TestEntity.class;
    }

    @Override
    public List<TestEntity> findAll()
    {
        return findAll(TestEntity.class);
    }

    @Override
    public TestEntity findEntityById(Long id)
    {
        return findEntityViaClassById(id, TestEntity.class);
    }

    public TestEntity findEntityByTitle(String title)
    {
        return findEntityViaClassByName("title", title, TestEntity.class);
    }

    @Override
    public TestEntity update(TestEntity entity)
    {
        return mergeEntity(entity);
    }

    @Override
    public boolean delete(Long id)
    {
        return deleteEntityViaClassById(id, TestEntity.class);
    }

    @Override
    public boolean create(TestEntity entity)
    {
        return mergeEntity(entity) != null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
