package ru.otus.ws;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.services.CachedUpdater;
import ru.otus.services.DataBroadcaster;
import ru.otus.servlets.ServletAwareConfig;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import static ru.otus.gwt.shared.Constants.ENDPOINT_PUBLIC;
import static ru.otus.gwt.shared.Constants.BROADCASTER_PUBLIC_SERVICE;

@ServerEndpoint(value = "/" + ENDPOINT_PUBLIC,  configurator = ServletAwareConfig.class)
public class PublicEndpoint
{
    private static final Logger LOGGER = LogManager.getLogger(PublicEndpoint.class.getName());
    private DataBroadcaster broadcaster;
    private EndpointConfig config;

    @OnMessage
    public void onMessage(Session session, String msg) {
        //provided for completeness, in out scenario clients don't send any msg.
        try {
            LOGGER.info("Received msg {} from {}.", msg, session.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DataBroadcaster getBroadcaster()
    {
        HttpSession httpSession = (HttpSession) config.getUserProperties().get("httpSession");
        LOGGER.info("httpSession {}.", httpSession);
        return (DataBroadcaster) httpSession.getServletContext().getAttribute(BROADCASTER_PUBLIC_SERVICE);
    }

    @OnOpen
    public void open(Session session, EndpointConfig config) {
        this.config = config;
        this.broadcaster = getBroadcaster();
        if (null != broadcaster) {
            broadcaster.registerDataUpdater(session, new CachedUpdater());
            LOGGER.info("New session opened: {}", session.getId());
        }
    }

    @OnError
    public void error(Session session, Throwable t) {
        if (null != broadcaster) {
            broadcaster.unregisterDataUpdater(session);
            LOGGER.error("Error on session {}", session.getId());
        }
    }

    @OnClose
    public void closedConnection(Session session) {
        if (null != broadcaster) {
            broadcaster.unregisterDataUpdater(session);
            LOGGER.info("Session closed: {}", session.getId());
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
