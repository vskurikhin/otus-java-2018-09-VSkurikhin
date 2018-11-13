package ru.otus.servlets;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.models.StatisticEntity;
import ru.otus.services.DbService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Optional;

import static ru.otus.gwt.shared.Constants.*;

@WebServlet("/" + REQUEST_COLLECT_STATISTIC)
public class CollectStatisticServlet extends HttpServlet
{
    private static final Logger LOGGER = LogManager.getLogger(CollectStatisticServlet.class.getName());

    private String getNameMarker()
    {
        return Optional.ofNullable(System.getenv(ENVIRONMENT_MARKER_NAME)).orElse(DEFAULT_MARKER_NAME);
    }

    void statFromRequestParams(HttpServletRequest request)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);

        StatisticEntity result = new StatisticEntity();
        result.setId(-1L);
        result.setNameMarker(getNameMarker());
        result.setJspPageName(request.getParameter(PARAMETER_PAGE_NAME));
        result.setIpAddress(request.getRemoteAddr());
        result.setUserAgent(request.getHeader(HEADER_USER_AGENT));
        result.setClientTime(LocalDateTime.parse(request.getParameter(PARAMETER_CLIENT_TIME)));
        result.setServerTime(LocalDateTime.now());
        result.setSessionId(request.getSession().getId());
        result.setUser(dbService.getUserEntityByName(request.getRemoteUser()));
        result.setPreviousId(null);

        dbService.saveEntity(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        LOGGER.info("doPost");
        try (PrintWriter pw = resp.getWriter()) {
            statFromRequestParams(req);
            resp.setHeader("Content-Type", "application/json; charset=UTF-8");
            pw.write("{\"visits_stat_id\":\"" + 1 + "\"}");
        } catch (Exception e) {
            LOGGER.error(e);
        }

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
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
