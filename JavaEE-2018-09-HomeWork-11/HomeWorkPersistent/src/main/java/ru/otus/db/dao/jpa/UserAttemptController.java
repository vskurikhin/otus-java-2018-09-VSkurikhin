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

import ru.otus.db.dao.UserAttemptDAO;
import ru.otus.models.entities.UserAttemptEntity;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;

@Remote(UserAttemptDAO.class)
@Stateless
@TransactionAttribute(SUPPORTS)
public class UserAttemptController extends AbstractController<UserAttemptEntity, Long> implements UserAttemptDAO
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
    protected Class<UserAttemptEntity> getTypeFirstParameterClass()
    {
        return UserAttemptEntity.class;
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public List<UserAttemptEntity> findAll()
    {
        return findAll(UserAttemptEntity.class);
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public UserAttemptEntity findEntityById(Long id)
    {
        return findEntityViaClassById(id, UserAttemptEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public UserAttemptEntity update(UserAttemptEntity entity)
    {
        return mergeEntity(entity);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        return deleteEntityViaClassById(id, UserAttemptEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean create(UserAttemptEntity entity)
    {
        return mergeEntity(entity) != null;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
