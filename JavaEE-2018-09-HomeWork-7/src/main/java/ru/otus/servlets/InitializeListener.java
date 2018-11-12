package ru.otus.servlets;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.services.*;
import ru.otus.services.SearchCacheService;
import ru.otus.services.SearchCacheServiceImpl;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import java.io.IOException;

import static ru.otus.gwt.shared.Constants.*;

@WebListener
public class InitializeListener implements ServletContextListener
{
    // @Resource(name = "jdbc/PostgresMyDB") // for Tomcat
    @Resource(lookup = "jdbc/PostgresMyDB") // for Glassfish
    private DataSource dataSource;

    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    // private static final EntityManagerFactory emf =
    // Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("On start web app ...");
        DbService dbService = new DbJPAPostgreSQLService(emf.createEntityManager());
        NewsService newsService = new RBCNewsService();
        ForexService forexService = new ForexCBRService();
        SearchCacheService cacheService = new SearchCacheServiceImpl();
        DirectoryService directoryService = new DirectoryJDBCService(dataSource);
        ServletContext sc = sce.getServletContext();

        sc.setAttribute(DB_SERVICE, dbService);
        sc.setAttribute(NEWS_SERVICE, newsService);
        sc.setAttribute(FOREX_SERVICE, forexService);
        sc.setAttribute(CACHE_SERVICE, cacheService);
        sc.setAttribute(DIRECTORY_SERVICE, directoryService);
        System.out.println("Ok initialized web app.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        DbService dbService = (DbService) sce.getServletContext().getAttribute(DB_SERVICE);
        try {
            dbService.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("On shutdown web app");
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
