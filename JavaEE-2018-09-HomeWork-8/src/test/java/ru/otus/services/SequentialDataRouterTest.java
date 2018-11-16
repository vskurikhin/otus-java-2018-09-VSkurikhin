package ru.otus.services;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SequentialDataRouterTest
{
    private SequentialDataRouter router;

    class TestDataOrigin implements DataOrigin
    {
        private boolean ready = false;

        @Override
        public boolean isReady()
        {
            return ready;
        }

        @Override
        public void fetchData()
        {
            ready = true;
        }

        @Override
        public String getDataXML()
        {
            return null;
        }

        @Override
        public String getDataJSON()
        {
            return null;
        }
    }

    @Before
    public void setUp() throws Exception
    {
        router = new SequentialDataRouter();
    }

    @After
    public void tearDown() throws Exception
    {
        router.close();
        router = null;
    }

    @Test
    public void updatePeriod()
    {
        int expected = 13;
        router.setUpdatePeriod(expected);
        Assert.assertEquals(expected, router.getUpdatePeriod());
    }

    @Test
    public void isRunning() throws InterruptedException
    {
        router.start();
        Thread.sleep(100);
        Assert.assertTrue(router.getRUNNING());
    }

    @Test
    public void registerDataOrigin() throws InterruptedException
    {
        TestDataOrigin origin = new TestDataOrigin();
        router.registerDataOrigin("TEST_DATA_ORIGIN", origin);
        router.start();
        Thread.sleep(100);
        Assert.assertTrue(origin.isReady());
    }

    @Test
    public void unregisterDataOrigin() throws InterruptedException
    {
        TestDataOrigin origin = new TestDataOrigin();
        router.registerDataOrigin("TEST_DATA_ORIGIN", origin);
        router.unregisterDataOrigin("TEST_DATA_ORIGIN");
        router.start();
        Thread.sleep(100);
        Assert.assertFalse(origin.isReady());
    }

    @Test
    public void registerDataUpdater()
    {
    }

    @Test
    public void unregisterDataUpdater()
    {
    }
}