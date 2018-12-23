package ru.otus.ejb;

import javax.ejb.Remote;

@Remote
public interface GameAttemptsService
{
    void doAttempt(String userName);
    int getAttemptsCount(String userName);
    boolean isLocked(String userName);
}
