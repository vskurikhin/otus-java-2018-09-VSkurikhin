package ru.otus.db.dao;

import ru.otus.models.entities.EmpEntity;

import javax.ejb.Local;

@Local
public interface EmpDAO extends DAOController<EmpEntity, Long>
{
    void updateFirstName(Long id, String firstName);

    void updateSecondName(Long id, String secondName);

    void updateSurName(Long id, String surName);

    long findMaxSalary();

    double calcAvgSalary();
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
