package ru.otus.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Singleton
@Startup
public class LockedUsers
{
    private Map<Long, LocalDateTime> attemptsMap;

    @PostConstruct
    public void initialize()
    {
        attemptsMap = new ConcurrentHashMap<>();
    }

    @Lock(LockType.WRITE)
    public void lock(long id, LocalDateTime time)
    {
        attemptsMap.put(id, time);
    }

    @Lock(LockType.READ)
    public boolean isLocked(long id)
    {
        LocalDateTime now = LocalDateTime.now().minusMinutes(1);
        LocalDateTime time = attemptsMap.get(id);

        if (null == time) {
            return false;
        }
        else if (time.compareTo(now) > 0) {
            attemptsMap.remove(id);

            return false;
        }

        return true;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
