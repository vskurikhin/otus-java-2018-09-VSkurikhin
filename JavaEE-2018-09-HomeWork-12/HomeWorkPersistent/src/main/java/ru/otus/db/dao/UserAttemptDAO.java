package ru.otus.db.dao;

import ru.otus.models.entities.UserAttemptEntity;

import java.util.List;

public interface UserAttemptDAO extends DAOController<UserAttemptEntity, Long>
{
    List<UserAttemptEntity> findEntitiesByUserId(long userId);

    long getAttemptsCount(long userId);

}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
