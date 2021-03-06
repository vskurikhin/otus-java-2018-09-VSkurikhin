/*
 * CreateUserEntity.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.function;

import ru.otus.models.entities.UserEntity;
import ru.otus.utils.NullString;

import java.util.function.Function;

public class CreateUserEntity implements Function<String[], UserEntity>
{
    /**
     * The Factory method is for build the UserEntity object from array of String's.
     *
     * @param fields array of String's.
     * @return new object.
     */
    @Override
    public UserEntity apply(String[] fields)
    {
        if (fields.length < 3) {
            throw new IndexOutOfBoundsException();
        }

        UserEntity entity = new UserEntity();

        entity.setId(Long.parseLong(fields[0]));
        entity.setLogin(fields[1]);
        entity.setPassword(NullString.returnString(fields[2]));

        return entity;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
