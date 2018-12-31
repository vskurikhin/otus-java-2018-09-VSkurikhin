# otus-java-2018-09-VSkurikhin
## Для курса "Разработчик Java Enterprise" в Otus.ru

#### Группа 2018-09
##### Виктор Скурихин (Victor Skurikhin)

### Домашнее задание 12
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
 ---
 * [x] Разработана стилизованная страница входа в приложение с поддержкой FORM-метода аутентификации:
 ```
 HomeWorkHTTP/src/main/webapp/WEB-INF/signin.jsp
 ```
   форма использует стандартные механизмы, предлагаемые JavaEE Security.
   Интерфейс производит авторизацию на основе данных, хранящихся на уровне БД
   (таблица пользователей users – логин и хэш-пароля).
   После успешной авторизации пользователю выданы права на основании связанной таблицы ролей user_groups.
 * [x] RESTful веб-сервис ``SecurityApiController``, предоставляющий возможность программной авторизации пользователя в приложении,
   а также возможного выхода из него.
 * [x] Добавлена кнопка «Выход» на всех авторизованных страницах приложения.
 * [x] Предусмотреть на выбор слушателя разграничение ролевого доступа к бизнес-логике
   сервлетов, EJB и т.д. Необходимо иметь, как минимум две основных роли: рядовой
   пользователь и администратор, разграничивающих доступ к функционалу системы.
 * [x] Разработана интерфейсная часть (``RoleResource`` и ``UserResource``) для управлением справочником ролей с возможностью присвоения их пользователям системы.
   Данный функционал доступен только администраторам системы.

