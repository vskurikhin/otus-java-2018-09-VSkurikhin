package ru.otus.utils;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;

import static ru.otus.gwt.shared.Constants.*;

public class BrowserInfo
{
    public static boolean isSupportedBrowser(HttpServletRequest request)
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

        return true;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
