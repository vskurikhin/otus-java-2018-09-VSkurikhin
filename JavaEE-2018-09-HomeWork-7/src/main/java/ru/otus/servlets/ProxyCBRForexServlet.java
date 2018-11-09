package ru.otus.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.services.ForexService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static ru.otus.gwt.shared.Constants.FOREX_SERVICE;
import static ru.otus.gwt.shared.Constants.REQUEST_FOREX_RATES;

@WebServlet(name = "proxyCBRForexServlet", urlPatterns = {"/" + REQUEST_FOREX_RATES})
public class ProxyCBRForexServlet extends HttpServlet
{
    private static final Logger LOGGER = LogManager.getLogger(ProxyCBRForexServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    {
        ServletContext sc = getServletContext();
        ForexService forexService = (ForexService) sc.getAttribute(FOREX_SERVICE);

        resp.setContentType("text/xml; charset=UTF-8");

        try (PrintWriter out = resp.getWriter()) {
            String rates = forexService.getCurrencyExchangeRatesXML(sc);
            out.println(rates);
            LOGGER.info("Gave: " + rates.substring(0, 10) + " ...");
        } catch (Exception e) {
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
