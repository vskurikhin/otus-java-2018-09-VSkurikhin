package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import javax.servlet.ServletContext;

public interface ForexService
{
    String getCurrencyExchangeRatesJSON(ServletContext sc) throws Exception;
    String getCurrencyExchangeRatesXML(ServletContext sc) throws Exception;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
