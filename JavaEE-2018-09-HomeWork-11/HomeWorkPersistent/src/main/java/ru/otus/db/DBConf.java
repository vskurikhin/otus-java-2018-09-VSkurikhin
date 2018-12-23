/*
 * DBConf.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.db;

import ru.otus.models.entities.*;
import ru.otus.models.*;
import ru.otus.utils.ResourceAsStream;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ru.otus.shared.Constants.DB.*;

public interface DBConf
{
    String userName = "dbuser";
    String dbName = "db";
    String password = "password";

    String[] TABLES = new String[]{
             TBL_USERS,
             TBL_DEPT_DIRECTORY,
             TBL_EMP_REGISTRY,
             TBL_USER_GROUPS,
             TBL_STATISTIC,
             TBL_USERS_ATTEMPTS,
    };

    String[] ORDER_OF_DELETE_TABLES = new String[]{
             TBL_EMP_REGISTRY,
             TBL_DEPT_DIRECTORY,
             TBL_STATISTIC,
             TBL_USER_GROUPS,
             TBL_USERS,
             TBL_USERS_ATTEMPTS,
    };

    String[] ORDER_OF_RESTART_SEQUENCE = new String[]{
             SEQ_NAME_DEPT,
             SEQ_NAME_EMP,
             SEQ_NAME_GROUP,
             SEQ_NAME_STATISTIC,
             SEQ_NAME_USER,
             SEQ_NAME_USER_ATTEMPT,
    };

    Map<String, String> FILE_LOCATIONS = new HashMap<String, String>()
    {{
        put(TBL_DEPT_DIRECTORY, "DeptEntitiesXMLDataFileLocation");
        put(TBL_EMP_REGISTRY, "EmpEntitiesXMLDataFileLocation");
        put(TBL_STATISTIC, "StatisticEntitiesXMLDataFileLocation");
        put(TBL_USER_GROUPS, "UsersgroupEntitiesXMLDataFileLocation");
        put(TBL_USERS, "UserEntitiesXMLDataFileLocation");
    }};

    Class<?>[] CLASSES = new Class<?>[]{
               DeptEntity.class,
               DeptEntities.class,
               EmpEntity.class,
               EmpEntities.class,
               GroupEntity.class,
               GroupEntities.class,
               StatisticEntity.class,
               StatisticEntities.class,
               UserEntity.class,
               UserEntities.class,
               UserAttemptEntity.class,
    };

    static <T extends Entities> ImporterSmallXML<T> createImporterXML(ResourceAsStream resourceStream)
    throws IOException, JAXBException
    {
        return new ImporterSmallXML<>(resourceStream, CLASSES);
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
