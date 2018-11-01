package ru.otus.gwt.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.dataset.EmpEntity;
import ru.otus.gwt.client.service.InsideService;
import ru.otus.gwt.shared.Emp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsideServiceImpl extends RemoteServiceServlet implements InsideService
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    // private static final EntityManagerFactory emf =
    //        Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish
    private static final String SELECT_EMPL_ENTITY = "SELECT empl FROM EmpEntity empl";
    private static final String UPDATE_EMP_FIRST_NAME = "UPDATE EmpEntity e SET e.firstName = :name WHERE e.id = :id";
    private static final String UPDATE_EMP_SECOND_NAME = "UPDATE EmpEntity e SET e.secondName = :name WHERE e.id = :id";
    private static final String UPDATE_EMP_SUR_NAME = "UPDATE EmpEntity e SET e.surName = :name WHERE e.id = :id";
    private static final Logger LOGGER = LogManager.getLogger(LoginServiceImpl.class.getName());

    private Emp convertEmpEntityToEmp(EmpEntity entity) {
        return new Emp(entity.getId(), entity.getFirstName(), entity.getSecondName(), entity.getSurName());
    }

    @Override
    public List<Emp> getEmpsList()
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            Query q = em.createQuery(SELECT_EMPL_ENTITY);
            //noinspection unchecked
            ArrayList<EmpEntity> list = new ArrayList<>(q.getResultList());
            transaction.commit();
            return list.stream().map(this::convertEmpEntityToEmp).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    @Override
    public void addNewEmp(Emp emp)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            EmpEntity entity = new EmpEntity();

            entity.setId(-1L);
            entity.setFirstName(emp.getFirstName());
            entity.setSecondName(emp.getSecondName());
            entity.setSurName(emp.getSurName());

            em.merge(entity);
            transaction.commit();
        }
        catch (Exception e) {
            LOGGER.debug(e);
            transaction.rollback();
            throw new RuntimeException(e);
        }
        finally {
            em.close();
        }

    }

    private void queryUpdate(long id, String query, String value)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query q = em.createQuery(query);
            q.setParameter("name", value);
            q.setParameter("id", id);
            q.executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            LOGGER.debug(e);
            transaction.rollback();
            throw new RuntimeException(e);
        }
        finally {
            em.close();
        }
    }

    @Override
    public void setEmpFirstName(long id, String value)
    {
        queryUpdate(id, UPDATE_EMP_FIRST_NAME, value);
    }

    @Override
    public void setEmpSecondName(long id, String value)
    {
        queryUpdate(id, UPDATE_EMP_SECOND_NAME, value);
    }

    @Override
    public void setEmpSurName(long id, String value)
    {
        queryUpdate(id, UPDATE_EMP_SUR_NAME, value);
    }

    @Override
    public void deleteEmp(long id)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            Query q = em.createQuery("DELETE FROM EmpEntity e WHERE e.id = :id");
            q.setParameter("id", id);
            q.executeUpdate();
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
            LOGGER.debug(e);
            throw new RuntimeException(e);
        }
        finally {
            em.close();
        }

    }
}