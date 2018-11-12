package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.models.EmpEntity;

import javax.servlet.ServletContext;
import java.io.Closeable;
import java.util.List;
import java.util.Map;

public interface DbService extends Closeable
{
    void clearDb(ServletContext sc) throws Exception;

    void importDb(ServletContext sc) throws Exception;

    void exportDb(ServletContext sc) throws Exception;

    List<EmpEntity> getEmpEntities();

    EmpEntity getEmpEntityById(long id);

    void saveEmpEntity(EmpEntity entity);

    void updateFirstNameInEmpEntityById(long id, String firstName);

    void updateSecondNameInEmpEntityById(long id, String secondName);

    void updateSurNameInEmpEntityById(long id, String surName);

    void deleteEmpEntityById(long id);

    List<EmpEntity> searchEmpEntity(Map<String, Object> attrs);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
