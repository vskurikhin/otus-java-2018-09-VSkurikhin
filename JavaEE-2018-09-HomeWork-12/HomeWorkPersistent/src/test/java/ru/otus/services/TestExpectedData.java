package ru.otus.services;

import ru.otus.models.DeptEntities;
import ru.otus.models.EmpEntities;
import ru.otus.models.entities.DeptEntity;
import ru.otus.models.entities.EmpEntity;
import ru.otus.models.entities.GroupEntity;
import ru.otus.models.entities.StatisticEntity;
import ru.otus.models.entities.UserEntity;

import java.time.LocalDateTime;

public class TestExpectedData
{
    public static final String directory =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator() +
            "<directory>" + System.lineSeparator() +
            "<organizationUnit id='1' title='title1'>" + System.lineSeparator() +
            "<organizationUnit id='2' title='title2'>" + System.lineSeparator() +
            "</organizationUnit>" + System.lineSeparator() +
            "<organizationUnit id='3' title='title3'>" + System.lineSeparator() +
            "</organizationUnit>" + System.lineSeparator() +
            "</organizationUnit>" + System.lineSeparator() +
            "</directory>";
    public static final String XML_TEST = "<?xml version='1.0' encoding='UTF-8'?><Test/>";
    public static final String JSON_TEST = "{Test:''}";
    public static final int DELAY_TEST = 300;

    public static ru.otus.models.entities.DeptEntity getTestDeptEntity1()
    {
        ru.otus.models.entities.DeptEntity result = new ru.otus.models.entities.DeptEntity();
        result.setId(1L);
        result.setParentId(0L);
        result.setTitle("title1");

        return result;
    }

    public static ru.otus.models.entities.DeptEntity getTestDeptEntity2()
    {
        ru.otus.models.entities.DeptEntity result = new ru.otus.models.entities.DeptEntity();
        result.setId(2L);
        result.setParentId(1L);
        result.setTitle("title2");

        return result;
    }

    public static ru.otus.models.entities.DeptEntity getTestDeptEntity3()
    {
        ru.otus.models.entities.DeptEntity result = new DeptEntity();
        result.setId(3L);
        result.setParentId(1L);
        result.setTitle("title3");

        return result;
    }

    public static DeptEntities getTestDeptEntitiesList()
    {
        DeptEntities result = new DeptEntities();
        result.add(getTestDeptEntity1());
        result.add(getTestDeptEntity2());
        result.add(getTestDeptEntity3());
        return result;
    }

    public static ru.otus.models.entities.EmpEntity getTestEmpEntity1()
    {
        ru.otus.models.entities.EmpEntity result = new ru.otus.models.entities.EmpEntity();
        result.setId(1L);
        result.setFirstName("Александр");
        result.setSecondName("Иванович");
        result.setSurName("Фунт");
        result.setCity("Одесса");
        result.setJob("Зицпредседатель");
        result.setSalary(15L);
        result.setAge(75L);

        result.setUser(getTestUserEntity1());
        result.setDepartment(getTestDeptEntity1());

        return result;
    }

    public static ru.otus.models.entities.EmpEntity getTestEmpEntity2()
    {
        ru.otus.models.entities.EmpEntity result = new ru.otus.models.entities.EmpEntity();
        result.setId(2L);
        result.setFirstName("Иван");
        result.setSecondName("Иванович");
        result.setSurName("Иванов");
        result.setCity("Самара");
        result.setJob("Директор");
        result.setSalary(10L);
        result.setAge(33L);

        result.setUser(null);
        result.setDepartment(getTestDeptEntity2());

        return result;
    }

    public static ru.otus.models.entities.EmpEntity getTestEmpEntity3()
    {
        ru.otus.models.entities.EmpEntity result = new EmpEntity();
        result.setId(3L);
        result.setFirstName("Светлана");
        result.setSecondName("Семёновна");
        result.setSurName("Семёнова");
        result.setCity("Воронеж");
        result.setJob("Бухгалтер");
        result.setSalary(9L);
        result.setAge(34L);

        result.setUser(null);
        result.setDepartment(getTestDeptEntity3());

        return result;
    }

    public static EmpEntities getTestEmpEntitiesList()
    {
        EmpEntities result = new EmpEntities();
        result.add(getTestEmpEntity1());
        result.add(getTestEmpEntity2());
        result.add(getTestEmpEntity3());
        return result;
    }

    public static GroupEntity getTestGroupEntity1()
    {
        ru.otus.models.entities.GroupEntity result = new ru.otus.models.entities.GroupEntity();
        result.setId(1L);
        result.setGroupname("users");
        result.setLogin("funt");

        return result;
    }

    public static ru.otus.models.entities.UserEntity getTestUserEntity1()
    {
        ru.otus.models.entities.UserEntity result = new UserEntity();
        result.setId(1L);
        result.letName("funt");
        result.setPassword("e1X6Nk2/9ydYmDO74y89BK0aNQmQnQjK59X46mMRV9Q=");

        return result;
    }

    private static void getTestStatisticEntity(ru.otus.models.entities.StatisticEntity result)
    {
        result.setNameMarker("DEFAULT_MARKER");
        result.setJspPageName("browsers");
        result.setIpAddress("127.0.0.1");
        result.setUserAgent("Wget/1.19.4 (linux-gnu)");
        result.setSessionId("0000000000000000000000000000");
    }

    public static ru.otus.models.entities.StatisticEntity getTestStatisticEntity1()
    {
        ru.otus.models.entities.StatisticEntity result = new ru.otus.models.entities.StatisticEntity();
        result.setId(1L);
        getTestStatisticEntity(result);
        result.setClientTime(null);
        result.setServerTime(null);
        result.setUser(null);
        result.setPreviousId(0L);
        return result;
    }

    public static ru.otus.models.entities.StatisticEntity getTestStatisticEntity2()
    {
        ru.otus.models.entities.StatisticEntity result = new ru.otus.models.entities.StatisticEntity();
        result.setId(2L);
        getTestStatisticEntity(result);
        result.setUser(getTestUserEntity1());
        result.setClientTime(LocalDateTime.MIN);
        result.setServerTime(LocalDateTime.MAX);
        result.setPreviousId(1L);
        return result;
    }

    public static ru.otus.models.entities.StatisticEntity getTestStatisticEntity3()
    {
        ru.otus.models.entities.StatisticEntity result = new StatisticEntity();
        result.setId(3L);
        getTestStatisticEntity(result);
        result.setUser(getTestUserEntity1());
        result.setClientTime(LocalDateTime.MIN);
        result.setServerTime(LocalDateTime.MAX);
        result.setPreviousId(1L);
        return result;
    }
}
