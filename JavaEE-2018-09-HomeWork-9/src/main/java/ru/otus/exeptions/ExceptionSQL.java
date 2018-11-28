/*
 * Created by VSkurikhin 28.11.18 20:26.
 * ExceptionSQL.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.exeptions;

import java.sql.SQLException;

public class ExceptionSQL extends RuntimeException
{
    public ExceptionSQL(SQLException e)
    {
        super(e);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
