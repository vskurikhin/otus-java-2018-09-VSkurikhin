package ru.otus.web;

import ru.otus.services.DbJPAPostgreSQLService;
import ru.otus.services.DbService;
import ru.otus.services.SearchCacheService;
import ru.otus.services.SearchCacheServiceImpl;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.io.IOException;

import static ru.otus.gwt.shared.Constants.CACHE_SERVICE;
import static ru.otus.gwt.shared.Constants.DB_SERVICE;

@WebListener
public class InitializeListener implements ServletContextListener
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    // private static final EntityManagerFactory emf =
    // Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish


    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        System.out.println("On start web app");
        DbService dbService = new DbJPAPostgreSQLService(emf.createEntityManager());
        SearchCacheService searchCacheService = new SearchCacheServiceImpl();
        ServletContext sc = sce.getServletContext();

        sc.setAttribute(DB_SERVICE, dbService);
        sc.setAttribute(CACHE_SERVICE, searchCacheService);
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
