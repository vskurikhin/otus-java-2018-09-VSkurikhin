package ru.otus.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

import static ru.otus.gwt.shared.Constants.TIMEOUT;
import static ru.otus.utils.IO.saveResultToTruncatedFile;

public class RBCNewsService extends DataOriginFetching
{
    private static final Logger LOGGER = LogManager.getLogger(RBCNewsService.class.getName());
    public static final String NEWS_URL_LOCATION = "RBCNewsServiceURLLocation";
    public static final String DATA_FILE_LOCATION = "RBCNewsServiceFileLocation";

    private ServletContext ctx;
    private String path = null;
    private String url = null;
    private AtomicBoolean ready = new AtomicBoolean();

    public RBCNewsService(ServletContext context)
    {
        this.ctx = context;
        this.path = ctx.getInitParameter(DATA_FILE_LOCATION);
        this.url = ctx.getInitParameter(NEWS_URL_LOCATION);
    }

    @Override
    public boolean isReady()
    {
        return ready.get();
    }

    void sbAppendElement(StringBuilder sb, Document doc, int i)
    {
        if (i != 1) sb.append(',');
            sb.append(" {\"Id\": \"news").append(i).append("\", \"Text\": \"")
              .append(doc.getElementsByClass("news-feed__item__title").get(i).text())
              .append("\"}\n");
    }

    String cutOutJsonFromDocument(Document document)
    {
        StringBuilder sb = new StringBuilder("{\"NewsArray\": [\n");
        sbAppendElement(sb, document, 1);
        IntStream.range(2, 5).forEach(i -> sbAppendElement(sb, document, i));

        return sb.append(" ] \n}\n").toString();
    }

    String getJsonFromURL(String url, int timeout) throws IOException
    {
        Document doc = Jsoup.parse(new URL(url), timeout);

        return cutOutJsonFromDocument(doc);
    }

    private void saveJsonToFile()
    {
        LOGGER.info("saveJsonToFile: ready.set(false)");

        try {
            String json = getJsonFromURL(url, TIMEOUT);
            ready.lazySet(false);
            saveResultToTruncatedFile(json, path);
            ready.set(true);
            LOGGER.info("saveJsonToFile: ready.set(true).");
        }
        catch (Exception e) {
            LOGGER.info("saveJsonToFile: catch({}): {}", e.getClass(), e);
        }
    }

    @Override
    public void fetchData()
    {
        saveJsonToFile();
    }

    @Override
    public String getDataXML()
    {
        return null; // TODO
    }

    @Override
    public String getDataJSON()
    {
        return loadFromFile(path, LOGGER);
    }
}
