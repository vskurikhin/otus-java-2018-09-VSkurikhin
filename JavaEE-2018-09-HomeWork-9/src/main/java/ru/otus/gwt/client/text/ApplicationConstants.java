/*
 * Created by VSkurikhin 28.11.18 20:28.
 * ApplicationConstants.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.gwt.client.text;

import com.google.gwt.i18n.client.Constants;

public interface ApplicationConstants extends Constants
{
    @Key("login.label.alt")
    String login_label_alt();

    @Key("logon.success")
    String logon_success();

    @Key("login.placeholder.alt")
    String login_placeholder_alt();

    @Key("password.label.alt")
    String password_label_alt();

    @Key("logon.button.alt")
    String logon_button_alt();

    @Key("form.header")
    String form_header();

    String title();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
