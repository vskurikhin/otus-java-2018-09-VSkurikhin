/*
 * Copyright (c) Victor N. Skurikhin 28.11.18 21:03.
 * DeptEntityJsonAdapter.java
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.json;

import ru.otus.adapters.DataSetAdapter;
import ru.otus.models.DeptEntity;

import javax.json.bind.adapter.JsonbAdapter;

public class DeptEntityJsonAdapter
implements JsonbAdapter<DeptEntity, String>, DataSetAdapter<DeptEntity>
{
    /**
     * The marshall method is convert the DeptEntity object to JSON.
     *
     * @param dept the DeptEntity object to JSON.
     * @return JSON String.
     * @throws Exception
     */
    @Override
    public String adaptToJson(DeptEntity dept) throws Exception
    {
        return marshalAdapter(dept);
    }

    /**
     * The unmarshall method is convert the JSON String to DeptEntity object.
     *
     * @param s the JSON String.
     * @return DeptEntity object.
     * @throws Exception
     */
    @Override
    public DeptEntity adaptFromJson(String s) throws Exception
    {
        return unmarshalAdapter(s, DeptEntity.class);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
