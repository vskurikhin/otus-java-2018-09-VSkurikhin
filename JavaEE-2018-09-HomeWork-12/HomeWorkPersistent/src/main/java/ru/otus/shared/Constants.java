/*
 * Constants.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.shared;

public class Constants
{
    public static final String PROC_INSERT_STATISTIC = "insert_statistic";

    public class DB
    {
        public static final String F_ID = "id";
        public static final String F_AGE = "age";
        public static final String F_ATTEMPTS_LEFT = "attempts_left";
        public static final String F_CITY = "city";
        public static final String F_CLIENT_TIME = "client_time";
        public static final String F_DEPARTMENT_ID = "department_id";
        public static final String F_FIRST_NAME = "first_name";
        public static final String F_GROUPNAME = "groupname";
        public static final String F_IP_ADDRESS = "ip_address";
        public static final String F_JOB = "job";
        public static final String F_JSP_PAGE_NAME = "jsp_page_name";
        public static final String F_LAST_ATTEMPT_TIME = "last_attempt_time";
        public static final String F_LOGIN = "login";
        public static final String F_NAME_MARKER = "name_marker";
        public static final String F_PASSWORD = "password";
        public static final String F_PID = "pid";
        public static final String F_PREV_ID = "prev_id";
        public static final String F_SALARY = "salary";
        public static final String F_SECOND_NAME = "second_name";
        public static final String F_SERVER_TIME = "server_time";
        public static final String F_SESSIONID = "session_id";
        public static final String F_SUR_NAME = "sur_name";
        public static final String F_TITLE = "title";
        public static final String F_USER_AGENT = "user_agent";
        public static final String F_USER_ID = "user_id";

        public static final String PRC_INSERT_STATISTIC = "insert_db_statistic";

        public static final String SEQ_NAME_DEPT = "dept_id_seq";
        public static final String SEQ_GENERATOR_DEPT = "dept_identifier ";

        public static final String SEQ_NAME_EMP = "emp_id_seq";
        public static final String SEQ_GENERATOR_EMP = "emp_identifier ";

        public static final String SEQ_NAME_GROUP = "user_group_id_seq";
        public static final String SEQ_GENERATOR_GROUP = "group_identifier ";

        public static final String SEQ_NAME_STATISTIC = "statistic_id_seq";
        public static final String SEQ_GENERATOR_STATISTIC = "statistic_identifier ";

        public static final String SEQ_NAME_USER = "user_id_seq";
        public static final String SEQ_GENERATOR_USER = "user_identifier ";

        public static final String SEQ_NAME_USER_ATTEMPT = "user_attempt_id_seq";
        public static final String SEQ_GENERATOR_USER_ATTEMPT = "user_attempt_identifier ";

        public static final String TBL_DEPT_DIRECTORY = "dept_directory";
        public static final String TBL_EMP_REGISTRY = "emp_registry";
        public static final String TBL_USER_GROUPS = "user_groups";
        public static final String TBL_STATISTIC = "statistic";
        public static final String TBL_USERS = "users";
        public static final String TBL_USERS_ATTEMPTS = "users_attempts";
    }

    public class WebUI
    {
        public static final String REQ_INSIDE_DEPT_LIST = "inside/request/dept/list";
        public static final String REQUEST_LOGOUT = "logout";
    }

    public class Rest
    {

        public static final String API_PATH_CREDIT_CATALOGS = "/catalogs";
        public static final String API_PATH_CATALOGS_LOCATIONS_V1 = "/locations/v1";
        public static final String API_PATH_CATALOGS_LOCATION_V1 = "/location/v1";
        public static final String FORM_PARAM_ID = "id";
        public static final String PATH_PARAM_ID = "id";
        public static final String FORM_PARAM_NAME = "name";

        public static final String API_PATH_CATALOGS_USERS_ROLES_V1 = "/users_roles/v1";
        public static final String API_PATH_CATALOGS_ROLES_V1 = "/roles/v1";
        public static final String FORM_PARAM_ROLE_ID = "role_id";
        public static final String FORM_PARAM_ACCOUNTS_ID_ARR = "accounts_id_arr";

        public static final String API_PATH_GEO_IP_CONTEXT = "/geo_ip_context";

        public static final String API_PATH_SECURITY = "/security";
        public static final String PATH_PARAM_USER_NAME = "username";
        public static final String PATH_PARAM_USER_PASS = "password";
        public static final String API_PATH_SECURITY_LOGIN = "/login";
        public static final String API_PATH_SECURITY_LOGOUT = "/logout";
    }

    public static final String CACHE_SERVICE = "CACHE_SERVICE";
    public static final String DB_SERVICE = "DB_SERVICE";
    public static final String FOREX_SERVICE = "FOREX_SERVICE";
    public static final String NEWS_SERVICE = "NEWS_SERVICE";
    public static final String BROADCASTER_PUBLIC_SERVICE = "BROADCASTER_PUBLIC_SERVICE";
    public static final String BROADCASTER_INSIDE_SERVICE = "BROADCASTER_INSIDE_SERVICE";
    public static final String STAT_SERVICE = "STAT_SERVICE";

    public static final String REQUEST_BROWSERS_JSP = "browsers.jsp";
    public static final String REQUEST_COLLECT_STATISTIC = "stats/insert";
    public static final String REQUEST_DIRECTORY = "directory";
    public static final String REQUEST_FOREX_RATES = "forex";
    public static final String REQUEST_NEWS = "news";
    public static final String REQUEST_VISITS_STAT_JSP = "inside/stat.do";
    public static final String REQUEST_CORPORATE_TAX = "corporate-tax";
    public static final String REQUEST_VISITS_SWITCH_COLLECTION = "inside/switch_collection_enabled";

    public static final String ENDPOINT_PUBLIC = "wspublic";
    public static final String ENDPOINT_INSIDE = "inside/ws";

    public static final String DEFAULT_MARKER_NAME = "DEFAULT_MARKER";
    public static final String ENVIRONMENT_MARKER_NAME = "MARKER_NAME";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String PARAMETER_CLIENT_TIME = "client_time";
    public static final String PARAMETER_PAGE_NAME = "page_name";
    public static final String PARAMETER_PREVIOS_ID = "prev_id";

    public static final String ATTR_BASE_URL = "base_url";
    public static final String ATTR_STAT_ELEMEMTS = "stat_elememts";
    public static final String ATTR_STAT_ENABLED = "stat_enabled";

    public static final String OPERA = "Opera";
    public static final String FIREFOX = "Firefox";
    public static final String CHROME = "Chrome";
    public static final String IE = "IE";
    public static final String SAFARI = "Safari";

    public static final int MIN_VERSION_OPERA = 38;
    public static final int MIN_VERSION_FIREFOX = 63;
    public static final int MIN_VERSION_CHROME = 50;
    public static final int MIN_VERSION_IE = 10;

    public static final String COOKIE_NAME_BROWSER_SUPPORT_CHECK_PASSED = "BrowserSupportCheckPassed";
    public static final String BROWSERS_VERSION_FILTER = "BrowsersVersionFilter";

    public static final String XML_ERROR = "<?xml version='1.0' encoding='UTF-8'?><Error/>";
    public static final String EMPTY = "";

    public static final int TIMEOUT = 800;

    public static final int DEFAULT_UPDATE_PERIOD = 30000; // 30s
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
