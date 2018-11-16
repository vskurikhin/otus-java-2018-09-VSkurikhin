package ru.otus.services;

import org.json.XML;
import org.junit.*;
import org.mockito.Mockito;

import javax.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static org.mockito.Mockito.mock;
import static ru.otus.utils.IO.readFile;

public class ForexCBRServiceTest
{
    static ServletContext ctx;
    private ForexCBRService service;

    @BeforeClass
    public static void setupServletContext()
    {
        File tmp = new File("target/" + ForexCBRServiceTest.class.getName() + ".xml");
        URI uri = tmp.toURI();
        ctx = mock(ServletContext.class);
        Mockito.doReturn(uri.toString())
               .when(ctx)
               .getInitParameter(ForexCBRService.DATA_FILE_LOCATION);
    }

    @Before
    public void setUp() throws Exception
    {
        service = new ForexCBRService(ctx);
    }

    @After
    public void tearDown() throws Exception
    {
        service = null;
    }

    private String getPathDataFileLocation() throws URISyntaxException
    {
        String uri = ctx.getInitParameter(ForexCBRService.DATA_FILE_LOCATION);
        return Paths.get(new URI(uri)).toString();
    }

    @Test
    public void fetchData()
    {
        fetchDataLoop();
        Assert.assertTrue(service.isReady());
    }

    public void fetchDataLoop()
    {
        for (int i = 0; i < 2 && !service.isReady(); ++i)
            service.fetchData();
    }

    @Test
    public void getDataXML() throws URISyntaxException, IOException
    {
        fetchDataLoop();

        String path = getPathDataFileLocation();
        String result = service.getDataXML();
        String expected = readFile(path, StandardCharsets.UTF_8);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.length() > 0);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDataJSON() throws URISyntaxException, IOException
    {
        fetchDataLoop();

        String path = getPathDataFileLocation();
        String expected = XML.toJSONObject(readFile(path, StandardCharsets.UTF_8)).toString();
        String result = service.getDataJSON();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.length() > 0);
        Assert.assertEquals(expected, result);
    }
}