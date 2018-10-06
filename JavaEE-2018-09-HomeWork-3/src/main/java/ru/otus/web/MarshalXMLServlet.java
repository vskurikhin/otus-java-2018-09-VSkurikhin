package ru.otus.web;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import ru.otus.dataset.EmpEntitiesList;
import ru.otus.dataset.EmpEntity;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

@WebServlet(urlPatterns = {"/marshal", "/marshal/*"})
public class MarshalXMLServlet extends HttpServlet
{
    private static final String PERSISTENCE_UNIT_NAME = "jpa";
    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    private static final String DATA_FILE_LOCATION = "testDataFileLocation";
    private static final String SELECT_EMPL_ENTITY = "SELECT empl FROM EmpEntity empl";
    static final String GET = "get";
    static final String OK = "ok";
    static final String SAVE = "save";

    private String retrieveCommand(HttpServletRequest request)
    {
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo.startsWith("/")) {
                return pathInfo.substring(1);
            }
        } catch (NullPointerException ignored) { }
        return null;
    }

    private void ok(PrintWriter out)
    {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
        out.println("<ok ok='ok'/>");
    }

    private EmpEntitiesList getEmpEntitiesList(EntityManager em, EntityTransaction transaction)
    {
        transaction.begin();
        Query q = em.createQuery(SELECT_EMPL_ENTITY);
        //noinspection unchecked
        ArrayList<EmpEntity> list = new ArrayList<EmpEntity>(q.getResultList());
        transaction.commit();
        return new EmpEntitiesList(list);
    }

    private void marshalEmpEntitiesList(PrintWriter out, EmpEntitiesList result, boolean save)
    {
        try {
            JAXBContext context = JAXBContext.newInstance(result.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if (save) {
                String path = getServletContext().getInitParameter(DATA_FILE_LOCATION);
                File file = Paths.get(new URI(path)).toFile();
                m.marshal(result, file);
            }
            m.marshal(result, out);

        } catch (JAXBException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException
    {
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();

        EntityManager em = emf.createEntityManager(); // for Tomcat
        EntityTransaction transaction = em.getTransaction();

        try {
            String command = retrieveCommand(request);
            if (command == null) {
                command = GET;
            }
            if (command.equals(OK)) {
                ok(out);
            } else {
                EmpEntitiesList result = getEmpEntitiesList(em, transaction);
                if (command.equals(SAVE)) {
                    marshalEmpEntitiesList(out, result, true);
                } else {
                    marshalEmpEntitiesList(out, result, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
