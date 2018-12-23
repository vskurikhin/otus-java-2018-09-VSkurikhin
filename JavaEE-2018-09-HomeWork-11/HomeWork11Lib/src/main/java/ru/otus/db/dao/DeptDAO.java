package ru.otus.db.dao;

import ru.otus.models.entities.DeptEntity;

public interface DeptDAO extends DAOController<DeptEntity, Long>
{
    DeptEntity findEntityByTitle(String title);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
