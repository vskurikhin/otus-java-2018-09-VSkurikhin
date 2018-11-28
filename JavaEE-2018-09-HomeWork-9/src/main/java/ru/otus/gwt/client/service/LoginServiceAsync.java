/*
 * Created by VSkurikhin 28.11.18 20:28.
 * LoginServiceAsync.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.service;

import com.google.gwt.user.client.rpc.AsyncCallback;
import ru.otus.gwt.shared.User;

public interface LoginServiceAsync
{
    void getMessage(String msg, AsyncCallback<String> async);
    void authorize(User user, AsyncCallback<Void> async);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
