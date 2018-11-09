package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.h2.jdbcx.JdbcDataSource;
import org.junit.*;
import ru.otus.models.DeptEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.sql.DataSource;

public class DirectoryJDBCServiceTest
{
    public static final String PERSISTENCE_UNIT_NAME = "test-jpa";
    private static final String JDBC_URL = "jdbc:h2:mem:test";
    private static EntityManagerFactory emf;
    private EntityManager entityManager;

    DirectoryJDBCService service;

    private DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(JDBC_URL);
        return dataSource;
    }

    @BeforeClass
    public static void createSchema() throws Exception {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    private void persist(long id, long pid, String title)
    {
        DeptEntity entity = new DeptEntity();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entity.setId(id);
        entity.setParentId(pid);
        entity.setTitle(title);
        entityManager.persist(entity);
        transaction.commit();
    }

    @Before
    public void setUp() throws Exception
    {
        entityManager = emf.createEntityManager();

        persist(1L, 0L, "title1");
        persist(2L, 1L, "title2");
        persist(3L, 1L, "title3");

        service = new DirectoryJDBCService(getDataSource());
    }

    @After
    public void tearDown() throws Exception
    {
        entityManager.close();
        service = null;
    }

    @Test
    public void getDirectoryXML() throws Exception
    {
        String result = service.getDirectoryXML(null);
        Assert.assertEquals(ExpectedData.directory, result);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
