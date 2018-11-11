package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.h2.jdbcx.JdbcDataSource;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;

import static ru.otus.services.TestDbJPAUtils.persistDeptEntity;

public class DirectoryJDBCServiceTest
{
    public static final String PERSISTENCE_UNIT_NAME = "test-jpa";
    private static final String JDBC_URL = "jdbc:h2:mem:test";
    private static EntityManagerFactory emf;
    private EntityManager em;

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

    @Before
    public void setUp() throws Exception
    {
        em = emf.createEntityManager();

        persistDeptEntity(em, 1L, 0L, "title1");
        persistDeptEntity(em, 2L, 1L, "title2");
        persistDeptEntity(em, 3L, 1L, "title3");

        service = new DirectoryJDBCService(getDataSource());
    }

    @After
    public void tearDown() throws Exception
    {
        em.close();
        service = null;
    }

    @Test
    public void getDirectoryXML() throws Exception
    {
        String result = service.getDirectoryXML(null);
        Assert.assertEquals(TestExpectedData.directory, result);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
