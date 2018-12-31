/*
 * UserController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

/*
 * This file was last modified at 30.11.18 0:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao.jpa;

import ru.otus.db.dao.RoleDAO;
import ru.otus.db.dao.UserDAO;
import ru.otus.models.entities.GroupEntity;
import ru.otus.models.entities.GroupEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static ru.otus.shared.Constants.DB.F_GROUPNAME;
import static ru.otus.shared.Constants.DB.F_LOGIN;

@Stateless
@TransactionAttribute(SUPPORTS)
public class RoleController extends AbstractController<GroupEntity, Long> implements RoleDAO
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public void setEntityManager(EntityManager em)
    {
        if (null == this.em) {
            this.em = em;
        }
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

    @Override
    @TransactionAttribute(SUPPORTS)
    public GroupEntity findEntityByName(String login)
    {
        return findEntityViaClassByName(F_GROUPNAME, login, GroupEntity.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
