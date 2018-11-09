package ru.otus.services;

import org.json.XML;
import org.junit.*;
import org.mockito.Mockito;

import javax.servlet.ServletContext;

import java.io.File;
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
        service = new ForexCBRService();
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
    public void testGetCurrencyExchangeRatesXML() throws Exception
    {
        String path = getPathDataFileLocation();
        String result = service.getCurrencyExchangeRatesXML(ctx);
        String expected = readFile(path, StandardCharsets.UTF_8);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.length() > 0);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void testGetCurrencyExchangeRatesJSON() throws Exception
    {
        String path = getPathDataFileLocation();
        String result = service.getCurrencyExchangeRatesJSON(ctx);
        String expected = XML.toJSONObject(readFile(path, StandardCharsets.UTF_8)).toString();

        Assert.assertNotNull(result);
        Assert.assertTrue(result.length() > 0);
        Assert.assertEquals(expected, result);
    }
}

