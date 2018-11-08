package ru.otus.web;

import org.apache.log4j.Logger;
import ru.otus.dataset.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

@WebServlet(name = "InitializeServlet", urlPatterns = "/init", loadOnStartup = 1)
public class InitialServlet extends HttpServlet
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";
    // private static final EntityManagerFactory emf =
    //        Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish
    private static final Logger logger = Logger.getLogger(InitialServlet.class.getName());

    private <T extends EntitiesList> T getEntitiesList(ServletConfig sc, String filename, Class<T> c)
    throws JAXBException
    {
        JAXBContext jc = JAXBContext.newInstance(
            EmpEntitiesList.class, UserEntitiesList.class, DeptEntitiesList.class, GroupEntitiesList.class,
            DeptEntity.class, EmpEntity.class, UserEntity.class, GroupEntity.class
        );
        Unmarshaller u = jc.createUnmarshaller();

        InputStream is = sc.getServletContext().getResourceAsStream(filename);

        //noinspection unchecked
        return (T) u.unmarshal(is);
    }

    private void saveDeptEntities(ServletConfig sc, EntityManager em) throws JAXBException
    {
        DeptEntitiesList list = getEntitiesList(sc, "/WEB-INF/classes/DeptEntities.xml", DeptEntitiesList.class);
        for (DeptEntity entity : list.getDepartments()) {
            em.merge(entity);
            logger.debug("entity: {}" + entity.toString());
        }
    }

    private void saveUserEntities(ServletConfig sc, EntityManager em) throws JAXBException
    {
        UserEntitiesList list = getEntitiesList(sc, "/WEB-INF/classes/UserEntities.xml", UserEntitiesList.class);
        for (UserEntity entity : list.getUsers()) {
            em.merge(entity);
            logger.debug("entity: {}" + entity.toString());
        }
    }

    private void saveEmpEntities(ServletConfig sc, EntityManager em) throws JAXBException
    {
        EmpEntitiesList list = getEntitiesList(sc, "/WEB-INF/classes/EmpEntities.xml", EmpEntitiesList.class);
        for (EmpEntity entity : list.getEmployees()) {
            em.merge(entity);
            logger.debug("entity: {}" + entity.toString());
        }
    }

    private void saveGroupEntities(ServletConfig sc, EntityManager em) throws JAXBException
    {
        GroupEntitiesList list = getEntitiesList(sc, "/WEB-INF/classes/UsersgroupEntities.xml", GroupEntitiesList.class);
        for (GroupEntity entity : list.getGroups()) {
            em.merge(entity);
            logger.debug("entity: {}" + entity.toString());
        }
    }

    @Override
    public void init(ServletConfig sc) throws ServletException
    {
        logger.info("InitialServlet starts");
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            saveDeptEntities(sc, em);
            saveUserEntities(sc, em);
            saveEmpEntities(sc, em);
            saveGroupEntities(sc, em);
            transaction.commit();
        } catch (JAXBException e) {
            transaction.rollback();
            logger.error(e);
        }
        finally {
            em.close();
        }
        logger.info("InitialServlet finish");

        super.init(sc);
    }

    @Override
    public void destroy() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.createNativeQuery("DELETE FROM emp_registry CASCADE").executeUpdate();
            em.createNativeQuery("DELETE FROM dep_directory CASCADE").executeUpdate();
            em.createNativeQuery("DELETE FROM users CASCADE").executeUpdate();
            em.createNativeQuery("DELETE FROM user_groups CASCADE").executeUpdate();
            transaction.commit();
        } finally {
            em.close();
        }
        logger.info("InitialServlet is dead");
        super.destroy();
    }
}

