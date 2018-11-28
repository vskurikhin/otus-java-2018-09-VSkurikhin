/*
 * Created by VSkurikhin 28.11.18 20:29.
 * DataSet.java
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
    String getName();
    void setName(String name);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
