/*
 * DataSet.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models;

import javax.persistence.*;

/**
 * Base Data Set class.
 */
@MappedSuperclass
public interface DataSet
{
    long getId();

    void setId(long id);

    String nameGet();

    void letName(String name);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
