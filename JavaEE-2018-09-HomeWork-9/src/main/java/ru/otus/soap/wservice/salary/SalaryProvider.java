/*
 * Created by VSkurikhin 28.11.18 23:24.
 * SalaryProvider.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wservice.salary;

import ru.otus.exeptions.ExceptionThrowable;

import javax.jws.WebMethod;
import javax.jws.WebResult;

public interface SalaryProvider
{
    @WebMethod
    @WebResult(name = "maxSalary")
    long getMaxSalary() throws ExceptionThrowable;

    @WebMethod
    @WebResult(name = "avgSalary")
    Double getAvgSalary() throws ExceptionThrowable;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
