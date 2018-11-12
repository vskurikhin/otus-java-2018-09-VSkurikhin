package ru.otus.services;

/*
 * Created by VSkurikhin at autumn 2018.
 */

import org.junit.*;
import org.mockito.Mockito;
import ru.otus.db.ImportSmallEntities;
import ru.otus.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static ru.otus.services.TestDbJPAUtils.*;
import static ru.otus.services.TestExpectedData.*;
import static ru.otus.utils.IO.getAbsolutePath;

public class DbJPAPostgreSQLServiceTest
{
    public static final String PERSISTENCE_UNIT_NAME = "test-jpa";
    private static final String TEST_RESOURCES = "src" + File.separator + "test" + File.separator + "resources"
                                               + File.separator;
    private static final String DEPT_ENTITIES_XML_DATA_FILE_LOCATION = TEST_RESOURCES + "DeptEntities.xml";
    private static final String USER_ENTITIES_XML_DATA_FILE_LOCATION = TEST_RESOURCES + "UserEntities.xml";
    private static final String EMP_ENTITIES_XML_DATA_FILE_LOCATION = TEST_RESOURCES + "EmpEntities.xml";
    private static final String GROUP_ENTITIES_XML_DATA_FILE_LOCATION = TEST_RESOURCES + "UsersgroupEntities.xml";
    private static final String FIRST_NAME = "FirstName";
    private static final String SECOND_NAME = "SecondName";
    private static final String SUR_NAME = "SurName";
    private static ImportSmallEntities<DeptEntitiesList> importDeptEntities;
    private static ImportSmallEntities<UserEntitiesList> importUserEntities;
    private static ImportSmallEntities<EmpEntitiesList> importEmpEntities;
    private static ImportSmallEntities<GroupEntitiesList> importGroupEntities;
    private static ServletContext ctx;
    private EntityManagerFactory emf;
    private EntityManager em;
    private DbService service;


    private static InputStream getInputStream(String relativePath) throws IOException
    {
        String path = getAbsolutePath(relativePath);
        return new FileInputStream(path);
    }

    private static void mockResourceAsStream(String fileLocationTest, String fileLocationMock)
    throws IOException
    {
        Mockito.doReturn(fileLocationTest)
               .when(ctx)
               .getInitParameter(fileLocationMock);
        Mockito.doReturn(getInputStream(fileLocationTest))
               .when(ctx)
               .getResourceAsStream(fileLocationTest);
    }

    private static void importXMLData() throws IOException, JAXBException
    {
        String deptEntitiesXMLPath = ctx.getInitParameter(DbJPAPostgreSQLService.DEPT_ENTITIES_XML_DATA_FILE_LOCATION);
        importDeptEntities = new ImportSmallEntities<>(ctx, deptEntitiesXMLPath);

        String userEntitiesXMLPath = ctx.getInitParameter(DbJPAPostgreSQLService.USER_ENTITIES_XML_DATA_FILE_LOCATION);
        importUserEntities = new ImportSmallEntities<>(ctx, userEntitiesXMLPath);

        String empEntitiesXMLPath = ctx.getInitParameter(DbJPAPostgreSQLService.EMP_ENTITIES_XML_DATA_FILE_LOCATION);
        importEmpEntities = new ImportSmallEntities<>(ctx, empEntitiesXMLPath);

        String groupEntitiesXMLPath = ctx.getInitParameter(DbJPAPostgreSQLService.GROUP_ENTITIES_XML_DATA_FILE_LOCATION);
        importGroupEntities = new ImportSmallEntities<>(ctx, groupEntitiesXMLPath);
    }

    @BeforeClass
    public static void setupServletContext() throws IOException, JAXBException
    {
        ctx = mock(ServletContext.class);
        mockResourceAsStream(DEPT_ENTITIES_XML_DATA_FILE_LOCATION,  DbJPAPostgreSQLService.DEPT_ENTITIES_XML_DATA_FILE_LOCATION);
        mockResourceAsStream(USER_ENTITIES_XML_DATA_FILE_LOCATION,  DbJPAPostgreSQLService.USER_ENTITIES_XML_DATA_FILE_LOCATION);
        mockResourceAsStream(EMP_ENTITIES_XML_DATA_FILE_LOCATION,   DbJPAPostgreSQLService.EMP_ENTITIES_XML_DATA_FILE_LOCATION);
        mockResourceAsStream(GROUP_ENTITIES_XML_DATA_FILE_LOCATION, DbJPAPostgreSQLService.GROUP_ENTITIES_XML_DATA_FILE_LOCATION);
        importXMLData();
    }

    @Before
    public void setUp() throws Exception
    {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = emf.createEntityManager();
        service = new DbJPAPostgreSQLService(em);
        importUserEntities.saveEntities(em);
        importDeptEntities.saveEntities(em);
        importEmpEntities.saveEntities(em);
        importGroupEntities.saveEntities(em);
    }

    @After
    public void tearDown() throws Exception
    {
        service.close();
        em.close();
        emf.close();
        service = null;
        em = null;
    }

    @Test
    public void clearDb() throws Exception
    {
        service.clearDb(null);
        Assert.assertEquals(0L, countRowsEntities(em, DeptEntity.class));
        Assert.assertEquals(0L, countRowsEntities(em, UserEntity.class));
        Assert.assertEquals(0L, countRowsEntities(em, EmpEntity.class));
        Assert.assertEquals(0L, countRowsEntities(em, GroupEntity.class));
    }

    /* Error java.io.IOException: Stream Closed!!!
    @Test
    public void importDb() throws Exception
    {
        service.importDb(ctx);

        Assert.assertEquals(3L, countRowsEntities(em, DeptEntity.class));
        Assert.assertEquals(1L, countRowsEntities(em, UserEntity.class));
        Assert.assertEquals(3L, countRowsEntities(em, EmpEntity.class));
        Assert.assertEquals(1L, countRowsEntities(em, GroupEntity.class));
    } */

    @Test
    public void getEntities() throws Exception
    {
        List<EmpEntity> result = service.getEmpEntities();
        for (EmpEntity entity : getTestEmpEntitiesList().asList()) {
            Assert.assertTrue(result.contains(entity));
        }
    }

    @Test
    public void getEmpEntityById() throws Exception
    {
        EmpEntity entity1 = service.getEmpEntityById(1L);
        Assert.assertEquals(getTestEmpEntity1(), entity1);
        EmpEntity entity2 = service.getEmpEntityById(2L);
        Assert.assertEquals(getTestEmpEntity2(), entity2);
        EmpEntity entity3 = service.getEmpEntityById(3L);
        Assert.assertEquals(getTestEmpEntity3(), entity3);
    }

    @Test
    public void saveEmpEntity() throws Exception
    {
        EmpEntity expected = getTestEmpEntity1();
        expected.setId(4L);
        expected.setFirstName(FIRST_NAME);
        expected.setSecondName(SECOND_NAME);
        expected.setSurName(SUR_NAME);
        service.saveEmpEntity(expected);
        EmpEntity entity = service.getEmpEntityById(4L);
        Assert.assertEquals(expected, entity);
    }

    @Test
    public void updateFirstNameInEmpEntityById() throws Exception
    {
        EmpEntity expected = service.getEmpEntityById(1L);
        expected.setFirstName(FIRST_NAME);
        service.updateFirstNameInEmpEntityById(1L, FIRST_NAME);
        EmpEntity test = service.getEmpEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void updateSecondNameInEmpEntityById() throws Exception
    {
        EmpEntity expected = service.getEmpEntityById(1L);
        expected.setSecondName(SECOND_NAME);
        service.updateSecondNameInEmpEntityById(1L, SECOND_NAME);
        EmpEntity test = service.getEmpEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test
    public void updateSurNameInEmpEntityById() throws Exception
    {
        EmpEntity expected = service.getEmpEntityById(1L);
        expected.setSurName(SUR_NAME);
        service.updateSecondNameInEmpEntityById(1L, SUR_NAME);
        EmpEntity test = service.getEmpEntityById(1L);
        Assert.assertEquals(expected, test);
    }

    @Test(expected = javax.persistence.NoResultException.class)
    public void deleteEmpEntityById() throws Exception
    {
        service.deleteEmpEntityById(1L);
        service.getEmpEntityById(1L);
        Assert.fail();
    }

    @Test
    public void searchEmpEntity()
    {
        Map<String, Object> attrs = new HashMap<>();
        attrs.put("name", "%Фунт%");
        List<EmpEntity> empEntities = service.searchEmpEntity(attrs);
        Assert.assertEquals(1, empEntities.size());
        EmpEntity entity = getTestEmpEntity1();
        Assert.assertTrue(empEntities.contains(entity));
        attrs.put("job", "%Зицпредседатель%");
        empEntities = service.searchEmpEntity(attrs);
        Assert.assertEquals(1, empEntities.size());
        entity = getTestEmpEntity1();
        Assert.assertTrue(empEntities.contains(entity));
        attrs.put("city", "%Одесса%");
        empEntities = service.searchEmpEntity(attrs);
        Assert.assertEquals(1, empEntities.size());
        entity = getTestEmpEntity1();
        Assert.assertTrue(empEntities.contains(entity));

        attrs.put("age", 75L);
        empEntities = service.searchEmpEntity(attrs);
        Assert.assertEquals(1, empEntities.size());
        entity = getTestEmpEntity1();
        Assert.assertTrue(empEntities.contains(entity));
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
