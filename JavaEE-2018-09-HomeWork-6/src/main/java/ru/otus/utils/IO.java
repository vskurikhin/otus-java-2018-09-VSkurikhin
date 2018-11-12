package ru.otus.utils;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IO
{
    public static String readInputStream(InputStream is, String encoding) throws IOException
    {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try  {
            br = new BufferedReader(new InputStreamReader(is, encoding));

            String line = br.readLine();

            while (line != null) {
                sb.append(line).append(System.lineSeparator());
                line = br.readLine();
            }

            return sb.toString();
        } catch (IOException e) {
            throw e;
        }
        finally {
            if (br != null) br.close();
            br = null;
        }
    }

    public static String getAbsolutePath(String relativePath)
    {
        return (new File(relativePath)).getAbsolutePath();
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
