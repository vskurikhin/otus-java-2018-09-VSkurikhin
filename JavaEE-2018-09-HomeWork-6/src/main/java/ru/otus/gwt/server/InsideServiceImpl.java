package ru.otus.gwt.server;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.dataset.EmpEntity;
import ru.otus.gwt.client.service.InsideService;
import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;
import ru.otus.services.DbService;
import ru.otus.services.SearchCacheService;

import javax.persistence.*;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.gwt.shared.Constants.CACHE_SERVICE;
import static ru.otus.gwt.shared.Constants.DB_SERVICE;

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
    private static final Logger LOGGER = LogManager.getLogger(InsideServiceImpl.class.getName());

    private Emp convertEmpEntityToEmp(EmpEntity entity) {
        return new Emp(
            entity.getId(), entity.getFirstName(), entity.getSecondName(), entity.getSurName(),
            entity.getJob(), entity.getCity(), entity.getAge().toString()
        );
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
            entity.setJob(emp.getJob());
            entity.setCity(emp.getCity());
            entity.setAge(Long.parseLong(emp.getAge()));

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

    public List<Emp> searchEmpInDb(Search search)
    {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        ArrayList<EmpEntity> list = null;

        try {
            transaction.begin();
            String sql = null;

            if (search.getFio() != null) {
                sql = "SELECT e FROM EmpEntity e WHERE (e.firstName LIKE :name OR e.secondName LIKE :name OR e.surName LIKE :name)";
                if (search.getJob() != null) {
                    sql += " AND e.job LIKE :job";
                }
                if (search.getCity() != null) {
                    sql += " AND e.city LIKE :city";
                }
                if (search.getAge() != null) {
                    sql += " AND e.age = :age";
                }
                Query q = em.createQuery(sql);

                q.setParameter("name", "%" + search.getFio() + "%");
                if (search.getJob() != null)
                    q.setParameter("job", "%" + search.getJob() + "%");
                if (search.getCity() != null)
                    q.setParameter("city", "%" + search.getCity() + "%");
                if (search.getAge() != null)
                    q.setParameter("age",  Long.parseLong(search.getAge()) );

                //noinspection unchecked
                list = new ArrayList<>(q.getResultList());
            }
            else if (search.getJob() != null) {
                sql = "SELECT e FROM EmpEntity e WHERE e.job LIKE :job";
                Query q = em.createQuery(sql);

                q.setParameter("job", "%" + search.getJob() + "%");
                if (search.getCity() != null) {
                    sql += " AND e.city LIKE :city";
                }
                if (search.getAge() != null) {
                    sql += " AND e.age = :age";
                }

                if (search.getCity() != null)
                    q.setParameter("city", "%" + search.getCity() + "%");
                if (search.getAge() != null)
                    q.setParameter("age",  Long.parseLong(search.getAge()) );

                //noinspection unchecked
                list = new ArrayList<>(q.getResultList());
            }
            else if (search.getCity() != null) {
                sql = "SELECT e FROM EmpEntity e WHERE e.city LIKE :city";
                Query q = em.createQuery(sql);

                q.setParameter("city", "%" + search.getCity() + "%");
                if (search.getAge() != null) {
                    sql += " AND e.age = :age";
                }

                if (search.getAge() != null)
                    q.setParameter("age",  Long.parseLong(search.getAge()) );

                //noinspection unchecked
                list = new ArrayList<>(q.getResultList());
            }
            else {
                list = new ArrayList<>();
            }

            transaction.commit();
            return list.stream().map(this::convertEmpEntityToEmp).collect(Collectors.toList());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Emp> searchEmp(Search search)
    {
        ServletContext sc = getServletContext();
        SearchCacheService cacheService = (SearchCacheService) sc.getAttribute(CACHE_SERVICE);

        List<Emp> result = cacheService.searchInCache(search);
        if (result != null) return result;

        LOGGER.info("Direct request to the database with hash: {}", search.hashCode());

        result = searchEmpInDb(search);
        cacheService.putToCache(search, result);

        return result;
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
