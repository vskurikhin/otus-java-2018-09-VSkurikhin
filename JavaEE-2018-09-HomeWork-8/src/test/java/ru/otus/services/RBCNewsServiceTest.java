package ru.otus.services;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.*;
import org.mockito.Mockito;

import javax.servlet.ServletContext;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import org.apache.http.testserver.HttpServer;

import static org.mockito.Mockito.mock;
import static ru.otus.utils.IO.readFile;

public class RBCNewsServiceTest
{
    static ServletContext ctx;
    private HttpServer server;
    private RBCNewsService service;

    private HttpRequestHandler myHttpRequestHandler = (request, response, context) -> {
        response.setEntity(new StringEntity(
            "<div class='news-feed__item__title'>test1</div>\n" +
            "<div class='news-feed__item__title'>test2</div>\n" +
            "<div class='news-feed__item__title'>test3</div>\n" +
            "<div class='news-feed__item__title'>test4</div>\n" +
            "<div class='news-feed__item__title'>test5</div>\n"
        ));
    };

    @BeforeClass
    public static void setupServletContext()
    {
        File tmp = new File("target/" + RBCNewsService.class.getName() + ".xml");
        URI uri = tmp.toURI();
        ctx = mock(ServletContext.class);
        Mockito.doReturn(uri.toString())
               .when(ctx)
               .getInitParameter(RBCNewsService.DATA_FILE_LOCATION);
    }

    @Before
    public void setUp() throws Exception
    {
        server = new HttpServer();
        server.registerHandler("/*", myHttpRequestHandler);
        server.start();
        int port = server.getPort();
        System.out.println("port = " + port);
        Mockito.doReturn("http://localhost:" + port)
                .when(ctx)
                .getInitParameter(RBCNewsService.NEWS_URL_LOCATION);
        service = new RBCNewsService(ctx);
    }

    @After
    public void tearDown() throws Exception
    {
        server.shutdown();
        service = null;
        server = null;
    }

    private String getPathDataFileLocation() throws URISyntaxException
    {
        String uri = ctx.getInitParameter(RBCNewsService.DATA_FILE_LOCATION);
        return Paths.get(new URI(uri)).toString();
    }

    @Test
    public void fetchData()
    {
        service.fetchData();

        Assert.assertTrue(service.isReady());
    }

    @Test
    public void getDataJSON() throws URISyntaxException, IOException
    {
        service.fetchData();

        String path = getPathDataFileLocation();
        String result = service.getDataJSON();
        String expected = readFile(path, StandardCharsets.UTF_8);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.length() > 0);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDataXML()
    {
        Assert.assertNull(service.getDataXML());
    }
}