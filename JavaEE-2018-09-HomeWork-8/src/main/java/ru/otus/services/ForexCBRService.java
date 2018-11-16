package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.XML;

import javax.servlet.ServletContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static ru.otus.gwt.shared.Constants.TIMEOUT;
import static ru.otus.utils.IO.converURIToAbsolutePath;
import static ru.otus.utils.IO.saveResultToTruncatedFile;

public class ForexCBRService implements DataOrigin
{
    private static final Logger LOGGER = LogManager.getLogger(ForexCBRService.class.getName());

    public static final String ENCODING = "windows-1251";
    public static final String CBR_DAILY_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final String DATA_FILE_LOCATION = "CBRForexServiceFileLocation";
    public static final String encoding1 = "encoding=\"" + ENCODING + '"';
    public static final String encoding2 = "encoding=\"UTF-8\"";
    public static final String XML_ERROR = "<?xml version='1.0' encoding='UTF-8'?><Error/>";
    public static final String EMPTY = "";

    private ServletContext ctx;
    private String path = null;
    private AtomicBoolean isReady = new AtomicBoolean(false);

    public ForexCBRService(ServletContext context)
    {
        this.ctx = context;
        this.path = ctx.getInitParameter(DATA_FILE_LOCATION);
    }

    private String loadXMLFromFile()
    {
        if (isReady.get()) {
            String aPath = null;
            StringBuilder sb = new StringBuilder();

            try {
                aPath = converURIToAbsolutePath(path);
            }
            catch (URISyntaxException e) {
                LOGGER.info("loadXMLFromFile: catch({}): {}", e.getClass(), e);
                return XML_ERROR;
            }

            try (BufferedReader br = new BufferedReader(new FileReader(aPath))) {
                String buffer = br.readLine();

                while (buffer != null) {
                    sb.append(buffer);
                    buffer = br.readLine();
                }

                String result = sb.toString();

                if (EMPTY.equals(result)) {
                    LOGGER.info("loadXMLFromFile: empty file: {}", path);
                    return XML_ERROR;
                }

                return result;
            }
            catch (IOException e) {
                LOGGER.info("loadXMLFromFile: catch({}): {}", e.getClass(), e);
            }
        }
        else {
            LOGGER.info("loadXMLFromFile: isn't ready!");
        }

        return XML_ERROR;
    }

    private String fetchXML(String encoding) throws IOException
    {
        URL url = new URL(CBR_DAILY_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(TIMEOUT);

        try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), encoding)) {
            return new BufferedReader(isr).lines().collect(Collectors.joining());
        }
    }

    private void fetchXMLToFile()
    {
        isReady.set(false);
        LOGGER.info("getCurrencyExchangeRates: isReady.set(false)");

        try {
            String xml = fetchXML(ENCODING).replaceFirst(encoding1, encoding2);
            saveResultToTruncatedFile(xml, path);

            isReady.set(true);
            LOGGER.info("getCurrencyExchangeRates: isReady.set(true).");
        }
        catch (Exception e) {
            LOGGER.info("getCurrencyExchangeRates: catch({}): {}", e.getClass(), e);
        }
    }

    @Override
    public boolean isReady()
    {
        return isReady.get();
    }

    @Override
    public void fetchData()
    {
        fetchXMLToFile();
    }

    @Override
    public String getDataXML()
    {
        return loadXMLFromFile();
    }

    @Override
    public String getDataJSON()
    {
        return XML.toJSONObject(getDataXML()).toString();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
