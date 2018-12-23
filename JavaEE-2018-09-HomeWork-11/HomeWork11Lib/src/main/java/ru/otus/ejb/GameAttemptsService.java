package ru.otus.ejb;

public interface GameAttemptsService
{
    void doAttempt(String userName);

    long getAttemptsCount(String userName);

    boolean isLocked(String userName);
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
