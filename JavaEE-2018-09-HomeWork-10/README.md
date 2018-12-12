# otus-java-2018-09-VSkurikhin
## Для курса "Разработчик Java Enterprise" в Otus.ru

#### Группа 2018-09
##### Виктор Скурихин (Victor Skurikhin)

### Домашнее задание 10
 * Настроки GlassFish:
   * Configurations -> server-config -> Security -> Realms
     Создать `jdbc-realm` со следующими параметрами:
     *     Realm Name:                    jdbc-realm
           Class Name:                    com.sun.enterprise.security.auth.realm.jdbc.JDBCRealm
           JAAS Context:                  jdbcRealm
           JNDI:                          jdbc/PostgresMyDB
           User Table:                    users
           User Name Column:              login
           Password Column:               password
           Group Table:                   user_groups
           Group Table User Name Column:  login
           Group Name Column:             groupname
           Password Encryption Algorithm: none
           Assign Groups:
           Database User:
           Database Password:
           Digest Algorithm:              SHA-256
           Encoding:                      Base64
           Charset:
 * Имя пользователя: `funt` 
 * Пароль: `funt`
 * Приложение декомпозированно на 3 модуля и два приложения.
   * Приложение HomeWork-10-webui - Client Tier.
   * Приложение HomeWork-10-rest
     * Модуль HomeWorkREST - Web RESTful Tier.
     * Модуль HomeWorkPersistent - Data Access Tier.
 * Создан RESTful-сервис - дифференцированный платеж - DiffPayResource. 
 * Создан RESTful-сервис - аннуитетный платеж - AnnuityPayResource.
 * Разработаны/отрефакторены справочники CRUD: DeptResource, EmpResource, StatisticResource и UserResource.
 * Разработать UI для работы с дифференцированным и аннуитетным платежами (Client Tier).
