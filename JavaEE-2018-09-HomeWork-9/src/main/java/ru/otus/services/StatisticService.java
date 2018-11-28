/*
 * Copyright (c) Victor N. Skurikhin 28.11.18 20:32.
 * StatisticService.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.services;

import ru.otus.models.StatisticEntity;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public interface StatisticService
{
    long saveStatisticFromRequestParams(HttpServletRequest request, DbService dbService);

    boolean isCollectionEnabled();

    List<StatisticEntity> getAllVisitsStatElements() throws SQLException;

    void setCollectionEnabled(boolean collectionEnabled);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
