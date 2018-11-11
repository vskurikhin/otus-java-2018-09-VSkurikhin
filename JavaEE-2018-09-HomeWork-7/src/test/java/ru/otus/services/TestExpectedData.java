package ru.otus.services;

import ru.otus.models.DeptEntity;
import ru.otus.models.EmpEntitiesList;
import ru.otus.models.EmpEntity;
import ru.otus.models.UserEntity;

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

    public static EmpEntity getTestEmpEntity1()
    {
        EmpEntity result = new EmpEntity();
        result.setId(1L);
        result.setFirstName("Александр");
        result.setSecondName("Иванович");
        result.setSurName("Фунт");
        result.setCity("Одесса");
        result.setJob("Зицпредседатель");
        result.setSalary(15L);
        result.setAge(75L);

        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setLogin("funt");
        user.setPassword("e1X6Nk2/9ydYmDO74y89BK0aNQmQnQjK59X46mMRV9Q=");
        result.setUser(user);

        DeptEntity dept = new DeptEntity();
        dept.setId(1L);
        dept.setTitle("title1");
        result.setDepartment(dept);

        return result;
    }

    public static EmpEntity getTestEmpEntity2()
    {
        EmpEntity result = new EmpEntity();
        result.setId(2L);
        result.setFirstName("Иван");
        result.setSecondName("Иванович");
        result.setSurName("Иванов");
        result.setCity("Самара");
        result.setJob("Директор");
        result.setSalary(10L);
        result.setAge(33L);
        result.setUser(null);

        DeptEntity dept = new DeptEntity();
        dept.setId(2L);
        dept.setParentId(1L);
        dept.setTitle("title2");
        result.setDepartment(dept);

        return result;
    }

    public static EmpEntity getTestEmpEntity3()
    {
        EmpEntity result = new EmpEntity();
        result.setId(3L);
        result.setFirstName("Светлана");
        result.setSecondName("Семёновна");
        result.setSurName("Семёнова");
        result.setCity("Воронеж");
        result.setJob("Бухгалтер");
        result.setSalary(9L);
        result.setAge(34L);
        result.setUser(null);

        DeptEntity dept = new DeptEntity();
        dept.setId(3L);
        dept.setParentId(1L);
        dept.setTitle("title3");
        result.setDepartment(dept);

        return result;
    }

    public static EmpEntitiesList getTestEmpEntitiesList()
    {
        EmpEntitiesList result = new EmpEntitiesList();
        result.add(getTestEmpEntity1());
        result.add(getTestEmpEntity2());
        result.add(getTestEmpEntity3());
        return result;
    }

}
