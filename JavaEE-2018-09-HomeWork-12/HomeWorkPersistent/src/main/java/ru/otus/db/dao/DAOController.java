/*
 * DAOController.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db.dao;

import ru.otus.models.DataSet;

import java.util.List;

public interface DAOController<E extends DataSet, K>
{
    List<E> findAll();

    E findEntityById(K id);

    E update(E entity);

    boolean delete(K id);

    boolean create(E entity);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
