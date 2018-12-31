package ru.otus.db.dao;

import ru.otus.models.entities.GroupEntity;
import ru.otus.models.entities.UserEntity;

import javax.ejb.Local;

@Local
public interface RoleDAO extends DAOController<GroupEntity, Long>
{
    GroupEntity findEntityByName(String login);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
