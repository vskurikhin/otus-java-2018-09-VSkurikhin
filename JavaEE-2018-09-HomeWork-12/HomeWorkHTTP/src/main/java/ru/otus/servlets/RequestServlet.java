/*
 * RequestServlet.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.db.dao.DeptDAO;
import ru.otus.models.entities.DeptEntity;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static ru.otus.shared.Constants.WebUI.REQ_INSIDE_DEPT_LIST;

@WebServlet("/" + REQ_INSIDE_DEPT_LIST)
public class RequestServlet extends HttpServlet
{
    private static final Logger LOGGER = LogManager.getLogger(RequestServlet.class.getName());

    @EJB(name = "DeptController")
    private DeptDAO dao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setContentType("text/plain; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            List<DeptEntity> depts = dao.findAll();
            req.setAttribute("depts", depts);
            req.getRequestDispatcher("/WEB-INF/dept.list.jsp").forward(req, resp);
        }
        catch (IOException | ServletException e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        super.doHead(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
