/*
 * Created by VSkurikhin 28.11.18 20:33.
 * CorporateTaxProvider.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.soap.wservice.corptax;

import javax.jws.WebMethod;
import javax.jws.WebResult;

public interface CorporateTaxProvider
{
    @WebMethod
    @WebResult(name = "currentTax")
    Double getCurrentTax(Double income, Double costs, Double taxRate);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
