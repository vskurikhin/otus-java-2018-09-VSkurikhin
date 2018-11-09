package ru.otus.services;

import org.junit.*;
import org.mockito.Mockito;

import javax.servlet.ServletContext;

import java.io.File;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import static org.mockito.Mockito.mock;
import static ru.otus.utils.IO.readFile;

public class RBCNewsServiceTest
{
    static ServletContext ctx;
    private RBCNewsService service;

    @BeforeClass
    public static void setupServletContext()
    {
        File tmp = new File("target/" + RBCNewsService.class.getName() + ".json");
        URI uri = tmp.toURI();
        ctx = mock(ServletContext.class);
        Mockito.doReturn(uri.toString())
               .when(ctx)
               .getInitParameter(RBCNewsService.DATA_FILE_LOCATION);
    }

    @Before
    public void setUp() throws Exception
    {
        service = new RBCNewsService();
    }

    @After
    public void tearDown() throws Exception
    {
        service = null;
    }

    @Test
    public void testGetNewsJSON() throws Exception
    {
        String uri = ctx.getInitParameter(RBCNewsService.DATA_FILE_LOCATION);
        String result = service.getNewsJSON(ctx);
        String path = Paths.get(new URI(uri)).toString();
        String expected = readFile(path, StandardCharsets.UTF_8);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.length() > 0);
        Assert.assertEquals(expected, result);
    }
}