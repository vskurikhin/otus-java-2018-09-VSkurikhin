/*
 * CreateDeptEntity.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.function;

import ru.otus.models.entities.DeptEntity;

import java.util.function.Function;

public class CreateDeptEntity implements Function<String[], DeptEntity>
{
    /**
     * The factory method is for build object of DeptEntity type from array of String's.
     *
     * @param fields array of String's.
     * @return new object of DeptEntity.
     */
    @Override
    public DeptEntity apply(String[] fields)
    {
        if (fields.length < 3) {
            throw new IndexOutOfBoundsException();
        }

        DeptEntity entity = new DeptEntity();

        entity.setId(Long.parseLong(fields[0]));
        entity.setParentId(Long.parseLong(fields[1]));
        entity.setTitle(fields[2]);

        return entity;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
