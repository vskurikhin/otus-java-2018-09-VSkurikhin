/*
 * Created by VSkurikhin 28.11.18 20:30.
 * EntitiesList.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.models;

import java.util.List;

public interface EntitiesList<T extends DataSet>
{
    void add(T emp);
    List<T> asList();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
