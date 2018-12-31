/*
 * GroupController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

/*
 * GroupController.java:
 * This file was last modified at 30.11.18 0:37 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.models.entities.GroupEntity;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.*;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Stateless
@LocalBean
@TransactionAttribute(SUPPORTS)
public class GroupController extends AbstractController<GroupEntity, Long>
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
    protected Class<GroupEntity> getTypeFirstParameterClass()
    {
        return GroupEntity.class;
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public List<GroupEntity> findAll()
    {
        return findAll(GroupEntity.class);
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public GroupEntity findEntityById(Long id)
    {
        return findEntityViaClassById(id, GroupEntity.class);
    }

    @TransactionAttribute(SUPPORTS)
    public GroupEntity findEntityByTitle(String title)
    {
        return findEntityViaClassByName("title", title, GroupEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public GroupEntity update(GroupEntity entity)
    {
        return mergeEntity(entity);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        return deleteEntityViaClassById(id, GroupEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean create(GroupEntity entity)
    {
        return mergeEntity(entity) != null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
