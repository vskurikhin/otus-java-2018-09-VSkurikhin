package ru.otus.db.dao;

import ru.otus.models.entities.StatisticEntity;

import javax.ejb.Local;

@Local
public interface StatisticDAO extends DAOController<StatisticEntity, Long>
{
    long insertProcedure(StatisticEntity entity);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
