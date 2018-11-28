/*
 * Created by VSkurikhin 28.11.18 20:26.
 * ExceptionURISyntax.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.exeptions;

import java.net.URISyntaxException;

public class ExceptionURISyntax extends URISyntaxException
{
    public ExceptionURISyntax(URISyntaxException e)
    {
        super(e.getInput(), e.getReason(), e.getIndex());
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
