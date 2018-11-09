package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import javax.servlet.ServletContext;
import java.io.Closeable;

public interface DbService extends Closeable
{
    void clearDb(ServletContext sc) throws Exception;
    void importDb(ServletContext sc) throws Exception;
    void exportDb(ServletContext sc) throws Exception;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
