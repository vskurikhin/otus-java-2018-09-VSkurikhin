package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static ru.otus.gwt.shared.Constants.DEFAULT_UPDATE_PERIOD;

public class SequentialDataRouter implements DataRouter
{
    private static final Logger LOGGER = LogManager.getLogger(SequentialDataRouter.class.getName());

    private final AtomicBoolean RUNNING = new AtomicBoolean(false);
    private final ConcurrentHashMap<String, DataOrigin> SOURCES = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Session, DataUpdater> HANDLERS = new ConcurrentHashMap<>();

    private int updatePeriod = DEFAULT_UPDATE_PERIOD;
    private Thread thread = null;

    public int getUpdatePeriod()
    {
        return updatePeriod;
    }

    public void setUpdatePeriod(int updatePeriod)
    {
        this.updatePeriod = updatePeriod;
    }

    public boolean getRUNNING()
    {
        return RUNNING.get();
    }

    @Override
    public void registerDataOrigin(String name, DataOrigin origin)
    {
        SOURCES.put(name, origin);
    }

    @Override
    public void unregisterDataOrigin(String name)
    {
        SOURCES.remove(name);
    }

    @Override
    public void registerDataUpdater(Session session, DataUpdater updater)
    {
        updater.setSession(session);
        updater.setSources(SOURCES);
        HANDLERS.put(session, updater);
    }

    @Override
    public void unregisterDataUpdater(Session session)
    {
        HANDLERS.remove(session);
    }

    private void fetchForDataOrigin(String name, DataOrigin origin)
    {
        LOGGER.info("fetchForDataOrigin for: {}", name);
        origin.fetchData();
    }

    private void sendToSession(Session session, DataUpdater updater)
    {
        LOGGER.info("sendToSession for: {}", session.getId());
        updater.handleOnDataUpdate();
    }

    private boolean sessionIsClosed(Map.Entry<Session, DataUpdater> entry)
    {
        return ! entry.getKey().isOpen();
    }

    private void run()
    {
        RUNNING.set(true);

        //noinspection InfiniteLoopStatement
        while (RUNNING.get()) {
            long startNs = System.nanoTime();

            LOGGER.info("run: iterate run in {}", this);

            SOURCES.forEach(this::fetchForDataOrigin);
            HANDLERS.forEach(this::sendToSession);
            HANDLERS.entrySet().removeIf(this::sessionIsClosed);

            long delta = (System.nanoTime() - startNs)/1_000_000;
            long delay = (updatePeriod - (delta < updatePeriod ? delta : 0)) / 10;

            try {
                for (int i = 0; i < 10 && RUNNING.get(); ++i)
                    Thread.currentThread().sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOGGER.info("run: catch({}): {}", e.getClass(), e);
            }
        }
    }

    @Override
    public void start()
    {
        thread = new Thread(this::run);
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void shutdown() throws InterruptedException
    {
        RUNNING.set(false);
        if (null != thread) {
            Thread.currentThread().sleep(updatePeriod);
            if (thread.isAlive()) thread.interrupt();
        }
    }

    @Override
    public void close() throws Exception
    {
        shutdown();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
