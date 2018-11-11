package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.XML;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.stream.Collectors;

import static ru.otus.gwt.shared.Constants.TIMEOUT;
import static ru.otus.utils.IO.saveResultToTruncatedFile;

public class ForexCBRService implements ForexService
{
    private static final Logger LOGGER = LogManager.getLogger(ForexCBRService.class.getName());

    public static final String ENCODING = "windows-1251";
    public static final String CBR_DAILY_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    public static final String DATA_FILE_LOCATION = "CBRForexServiceFileLocation";

    private String getXMLString(String encoding) throws IOException
    {
        URL url = new URL(CBR_DAILY_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(TIMEOUT);

        try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(), encoding)) {
            return new BufferedReader(isr).lines().collect(Collectors.joining());
        }
    }

    @Override
    public String getCurrencyExchangeRatesXML(ServletContext sc) throws Exception
    {
        String result = "";
        String path = sc.getInitParameter(DATA_FILE_LOCATION);
        final String encoding1 = "encoding=\"windows-1251\"";
        final String encoding2 = "encoding=\"UTF-8\"";

        try {
            result = getXMLString(ENCODING).replaceFirst(encoding1, encoding2);
            if (null == path) {
                LOGGER.debug("path is null");
                return result;
            }
            saveResultToTruncatedFile(result, path);
        }
        catch (IOException e1) {
            LOGGER.debug("e1: {}", e1);
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                result += br.readLine();
            }
            catch (Exception e2) {
                LOGGER.debug("e2: {}", e2);
                e2.initCause(e1);
                throw e2;
            }
        }

        return result;
    }

    @Override
    public String getCurrencyExchangeRatesJSON(ServletContext sc) throws Exception
    {
        return XML.toJSONObject(getCurrencyExchangeRatesXML(sc)).toString();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
