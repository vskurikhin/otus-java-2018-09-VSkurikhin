package ru.otus.ejb;

import ru.otus.models.entities.UserAttemptEntity;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameAttemptsServiceImpl implements GameAttemptsService
{
    private Map<Long, UserAttemptEntity> attemptsMap;

    @PostConstruct
    private void init() {
        attemptsMap = new ConcurrentHashMap<>();
    }

    @Override
    public void doAttempt(String userName)
    {

    }

    @Override
    public int getAttemptsCount(String userName)
    {
        return 0;
    }

    @Override
    public boolean isLocked(String userName)
    {
        return false;
    }
}
