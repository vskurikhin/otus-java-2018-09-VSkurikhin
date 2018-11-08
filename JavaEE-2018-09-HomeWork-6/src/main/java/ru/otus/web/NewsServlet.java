package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.IntStream;

@WebServlet("/rbcnews")
public class NewsServlet extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        Document doc;
        response.setContentType("text/json; charset=UTF-8");

        try (PrintWriter pw = response.getWriter()) {

            doc = Jsoup.connect("https://www.rbc.ru/").get();
            String mycallback = request.getParameter("mycallback");
            pw.println(mycallback + "({\"NewsArray\": [");
            IntStream.range(1, 5).forEach(i -> {
                pw.print(
                    " {\"Id\": \"news" + i + "\", \"Text\": \"" +
                    doc.getElementsByClass("news-feed__item__title")
                       .get(i).text() + "\"},"
                );
            });

            pw.println(" {\"Id\": \"end\", \"Text\": \"\"}");
            pw.println("]});");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
