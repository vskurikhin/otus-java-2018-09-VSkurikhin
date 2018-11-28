/*
 * Copyright (c) Victor N. Skurikhin 28.11.18 20:32.
 * InitialServlet.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.servlets;

import ru.otus.services.DbService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import static ru.otus.gwt.shared.Constants.DB_SERVICE;

@WebServlet(name = "InitializeServlet", urlPatterns = "/initialize", loadOnStartup = 1)
public class InitialServlet extends HttpServlet
{
    @Override
    public void init(ServletConfig config) throws ServletException
    {
        ServletContext sc = config.getServletContext();
        DbService dbService = (DbService) sc.getAttribute(DB_SERVICE);
        try {
            dbService.importDb(sc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.init(config);
    }

    @Override
    public void destroy()
    {
        ServletContext sc = getServletContext();
        DbService dbService = (DbService) sc.getAttribute(DB_SERVICE);
        try {
            dbService.clearDb(sc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.destroy();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
