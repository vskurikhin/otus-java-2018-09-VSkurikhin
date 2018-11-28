/*
 * Created by VSkurikhin 28.11.18 20:30.
 * DataBroadcaster.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import javax.websocket.Session;

public interface DataBroadcaster extends AutoCloseable
{
    void registerDataOrigin(String name, DataOrigin origin);

    void unregisterDataOrigin(String name);


    void registerDataUpdater(Session session, DataUpdater updater);

    void unregisterDataUpdater(Session session);


    void start();

    void shutdown() throws InterruptedException;
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
