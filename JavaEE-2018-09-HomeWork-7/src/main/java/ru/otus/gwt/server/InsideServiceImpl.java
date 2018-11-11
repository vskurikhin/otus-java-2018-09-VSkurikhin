package ru.otus.gwt.server;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.models.EmpEntity;
import ru.otus.gwt.client.service.InsideService;
import ru.otus.gwt.shared.Emp;
import ru.otus.gwt.shared.Search;
import ru.otus.services.DbService;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ru.otus.gwt.shared.Constants.DB_SERVICE;

public class InsideServiceImpl extends RemoteServiceServlet implements InsideService
{
    public static final String PERSISTENCE_UNIT_NAME = "jpa";

    // private static final EntityManaghomework5erFactory emf =
    //        Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // for Tomcat
    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf; // for Glassfish
    private static final Logger LOGGER = LogManager.getLogger(LoginServiceImpl.class.getName());

    private Emp convertEmpEntityToEmp(EmpEntity entity) {
        return new Emp(
            entity.getId(), entity.getFirstName(), entity.getSecondName(), entity.getSurName(),
            entity.getJob(), entity.getCity(), entity.getAge().toString()
        );
    }

    @Override
    public List<Emp> getEmpsList()
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        List<EmpEntity> list = dbService.getEmpEntities();
        return list.stream().map(this::convertEmpEntityToEmp).collect(Collectors.toList());
    }

    private EmpEntity convertTOEmpEntity(Emp emp)
    {
        EmpEntity entity = new EmpEntity();

        entity.setId(-1L);
        entity.setFirstName(emp.getFirstName());
        entity.setSecondName(emp.getSecondName());
        entity.setSurName(emp.getSurName());
        entity.setJob(emp.getJob());
        entity.setCity(emp.getCity());
        entity.setAge(Long.parseLong(emp.getAge()));

        return entity;
    }

    @Override
    public void addNewEmp(Emp emp)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.saveEmpEntity(convertTOEmpEntity(emp));
    }

    @Override
    public void setEmpFirstName(long id, String value)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.updateFirstNameInEmpEntityById(id, value);
    }

    @Override
    public void setEmpSecondName(long id, String value)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.updateSecondNameInEmpEntityById(id, value);
    }

    @Override
    public void setEmpSurName(long id, String value)
    {
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.updateSecondNameInEmpEntityById(id, value);
    }

    @Override
    public void deleteEmp(long id)
    {
        LOGGER.info("EmpEntity deliting by id: {} ...");
        DbService dbService = (DbService) getServletContext().getAttribute(DB_SERVICE);
        dbService.deleteEmpEntityById(id);
        LOGGER.info("EmpEntity by id: {} deleted.");
    }

    @Override
    public List<Emp> searchEmp(Search search)
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
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
