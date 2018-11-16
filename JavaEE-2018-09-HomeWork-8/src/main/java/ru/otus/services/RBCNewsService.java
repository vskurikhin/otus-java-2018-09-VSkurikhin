package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.URL;
import java.util.stream.IntStream;

import static ru.otus.gwt.shared.Constants.TIMEOUT;
import static ru.otus.utils.IO.saveResultToTruncatedFile;

public class RBCNewsService implements NewsService
{
    private static final Logger LOGGER = LogManager.getLogger(RBCNewsService.class.getName());
    public static final String NEWS_URL = "http://www.rbc.ru/";
    public static final String DATA_FILE_LOCATION = "RBCNewsServiceFileLocation";

    private void sbAppendElement(StringBuilder sb, Document doc, int i)
    {
        if (i != 1) sb.append(',');
        sb.append(" {'Id': 'news").append(i).append("', 'Text': '")
          .append(doc.getElementsByClass("news-feed__item__title").get(i).text())
          .append("'}");
    }

    @Override
    public String getNewsJSON(ServletContext sc) throws Exception
    {
        String result = "";
        String path = sc.getInitParameter(DATA_FILE_LOCATION);

        if (null == path) LOGGER.debug("path is null");
        try {
            Document doc = Jsoup.parse(new URL(NEWS_URL), TIMEOUT);
            StringBuilder sb = new StringBuilder("{'NewsArray': [");
            sbAppendElement(sb, doc, 1);
            IntStream.range(2, 5).forEach(i -> sbAppendElement(sb, doc, i));
            result = sb.append("]}").toString();
            if (null == path) return result;
            saveResultToTruncatedFile(result, path);
        }
        catch (Exception e1) {
            LOGGER.error("e1: {}", e1);
            if (null == path) return result;
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                result += br.readLine();
            }
            catch (Exception e2) {
                LOGGER.error("e2: {}", e2);
                e2.initCause(e1);
                throw e2;
            }
        }

        return result;
    }

    @Override
    public String getNewsXML(ServletContext sc) throws Exception
    {
        return null; // TODO
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
