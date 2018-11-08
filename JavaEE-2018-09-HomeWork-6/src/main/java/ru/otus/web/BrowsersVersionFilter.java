package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import static ru.otus.utils.Constants.*;

@WebFilter(filterName = "BrowsersVersionFilter", urlPatterns = "/*")
public class BrowsersVersionFilter extends HttpFilter
{
    private static final Logger LOGGER = LogManager.getLogger(BrowsersVersionFilter.class.getName());

    private static final String OPERA = "Opera";
    private static final String FIREFOX = "Firefox";
    private static final String CHROME = "Chrome";
    private static final String IE = "IE";
    private static final String SAFARI = "Safari";
    private static final int MIN_VERSION_OPERA = 38;
    private static final int MIN_VERSION_FIREFOX = 63;
    private static final int MIN_VERSION_CHROME = 50;
    private static final int MIN_VERSION_IE = 10;
    private static final String COOKIE_NAME_BROWSER_SUPPORT_CHECK_PASSED = "BrowserSupportCheckPassed";

    private boolean isSupportedBrowser(HttpServletRequest request)
    {
        String userAgentHeader = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentHeader);
        Browser browser = userAgent.getBrowser();
        String browserName = browser.getName();
        Version browserVersion = userAgent.getBrowserVersion();

        switch (browserName) {
            case CHROME :
                return Integer.parseInt(browserVersion.getMajorVersion()) > MIN_VERSION_CHROME;
            case FIREFOX :
                return Integer.parseInt(browserVersion.getMajorVersion()) > MIN_VERSION_FIREFOX;
            case IE :
                return Integer.parseInt(browserVersion.getMajorVersion()) > MIN_VERSION_IE;
            case OPERA :
                return Integer.parseInt(browserVersion.getMajorVersion()) > MIN_VERSION_OPERA;
        }

        return false;
    }

    private boolean isBrowserSupportCheckAlreadyPassed(HttpServletRequest request)
    {
        Cookie[] cookies = request.getCookies();

        return cookies != null && Arrays.stream(cookies).anyMatch(
               cookie -> cookie.getName().equals(COOKIE_NAME_BROWSER_SUPPORT_CHECK_PASSED)
        );
    }

    private boolean isDecorRequest(HttpServletRequest request)
    {
        String url = request.getRequestURL().toString();

        return url.contains("/css/") || url.contains("/img/") || url.contains("/webfonts/");
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws IOException, ServletException
    {
        if (isDecorRequest(request)) {
            chain.doFilter(request, response);
        }
        else if (isBrowserSupportCheckAlreadyPassed(request) || isSupportedBrowser(request)) {
            LOGGER.debug("Browser supported.");
            response.addCookie(new Cookie(COOKIE_NAME_BROWSER_SUPPORT_CHECK_PASSED, "1"));
            LOGGER.debug("Cookie added.");
            chain.doFilter(request, response);
        }
        else {
            LOGGER.debug("Browser not supported. Forward ...");
            request.getRequestDispatcher("/" + REQUEST_BROWSERS_JSP).forward(request, response);
            LOGGER.debug("Never logged.");
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
