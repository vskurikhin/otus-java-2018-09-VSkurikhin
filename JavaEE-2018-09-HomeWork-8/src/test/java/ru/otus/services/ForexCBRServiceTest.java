package ru.otus.services;

import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HttpRequestHandler;
import org.apache.http.testserver.HttpServer;
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
    private HttpServer server;
    private ForexCBRService service;

    private HttpRequestHandler myHttpRequestHandler = (request, response, context) -> {
        response.setEntity(new StringEntity(
            "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<ValCurs Date='17.11.2018' name='Foreign Currency Market'>\n" +
            " <Valute ID='R01035'>\n" +
            "  <NumCode>826</NumCode>\n" +
            "  <CharCode>GBP</CharCode>\n" +
            "  <Nominal>1</Nominal>\n" +
            "  <Name>Фунт стерлингов Соединенного королевства</Name>\n" +
            "  <Value>84,6296</Value>\n" +
            " </Valute>\n" +
            " <Valute ID='R01060'>\n" +
            "  <NumCode>051</NumCode>\n" +
            "  <CharCode>AMD</CharCode>\n" +
            "  <Nominal>100</Nominal>\n" +
            "  <Name>Армянских драмов</Name>\n" +
            "  <Value>13,5928</Value>\n" +
            " </Valute>\n" +
            " <Valute ID='R01235'>\n" +
            "  <NumCode>840</NumCode>\n" +
            "  <CharCode>USD</CharCode>\n" +
            "  <Nominal>1</Nominal>\n" +
            "  <Name>Доллар США</Name>\n" +
            "  <Value>65,9931</Value>\n" +
            " </Valute>\n" +
            "</ValCurs>"
        ));
        response.setHeader("Content-Type", "text/xml; charset=" + ForexCBRService.ENCODING);
    };
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
        server = new HttpServer();
        server.registerHandler("/*", myHttpRequestHandler);
        server.start();
        int port = server.getPort();
        System.out.println("port = " + port);
        Mockito.doReturn("http://localhost:" + port)
                .when(ctx)
                .getInitParameter(ForexCBRService.CBR_DAILY_URL_LOCATION);
        service = new ForexCBRService(ctx);
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
        String uri = ctx.getInitParameter(ForexCBRService.DATA_FILE_LOCATION);
        return Paths.get(new URI(uri)).toString();
    }

    public void fetchDataLoop()
    {
        for (int i = 0; i < 3 && ! service.isReady(); ++i)
            service.fetchData();
    }

    @Test
    public void fetchData()
    {
        service.fetchData();
        Assert.assertTrue(service.isReady());
    }

    @Test
    public void getDataXML() throws URISyntaxException, IOException
    {
        service.fetchData();

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