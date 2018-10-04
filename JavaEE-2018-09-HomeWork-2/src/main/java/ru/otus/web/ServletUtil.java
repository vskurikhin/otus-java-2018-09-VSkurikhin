package ru.otus.web;

import java.io.PrintWriter;

public class ServletUtil
{
    public static StringBuilder outHTMLHeader(PrintWriter out, String title)
    {
        StringBuilder sb = new StringBuilder("<html>\n<head>\n<html>\n<head>\n")
            .append("    <title>").append(title).append("</title>\n")
            .append("    <meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />\n")
            .append("</head>\n").append("<body>\n")
            .append("    <h2>").append(title).append("</h2>");
        return sb;
    }
}
