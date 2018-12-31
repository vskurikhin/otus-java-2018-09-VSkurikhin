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

import ru.otus.db.dao.UserDAO;
import ru.otus.models.entities.UserEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.*;
import java.util.List;

import static javax.ejb.TransactionAttributeType.REQUIRES_NEW;
import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static ru.otus.shared.Constants.DB.F_LOGIN;

@Stateless
@TransactionAttribute(SUPPORTS)
public class UserController extends AbstractController<UserEntity, Long> implements UserDAO
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
    protected Class<UserEntity> getTypeFirstParameterClass()
    {
        return UserEntity.class;
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public List<UserEntity> findAll()
    {
        return findAll(UserEntity.class);
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public UserEntity findEntityById(Long id)
    {
        return findEntityViaClassById(id, UserEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public UserEntity update(UserEntity entity)
    {
        return mergeEntity(entity);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean delete(Long id)
    {
        return deleteEntityViaClassById(id, UserEntity.class);
    }

    @Override
    @TransactionAttribute(REQUIRES_NEW)
    public boolean create(UserEntity entity)
    {
        return mergeEntity(entity) != null;
    }

    @Override
    @TransactionAttribute(SUPPORTS)
    public UserEntity findEntityByLogin(String login)
    {
        return findEntityViaClassByName(F_LOGIN, login, UserEntity.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
