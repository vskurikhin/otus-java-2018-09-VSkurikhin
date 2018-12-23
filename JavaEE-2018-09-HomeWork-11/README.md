# otus-java-2018-09-VSkurikhin
## Для курса "Разработчик Java Enterprise" в Otus.ru

#### Группа 2018-09
##### Виктор Скурихин (Victor Skurikhin)

### Домашнее задание 11
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
 * Переписаны основные компоненты системы c логикой их работы в EJB
````
        DeptController, EmpController, GroupController, StatisticController,
        UserAttemptController, UserController, AnnuityPayResource, DeptResource,
        DiffPayResource, EmpResource, StatisticResource, UserResource.
````
 * Создано новое, на том же аппликейшн сервере, web-приложениe ``HomeWork11WebUI``,
   это приложение создаёт запросы к EJB-бинам (DeptController и GameAttemptsService) главного приложения,
   с последующим отображением полученной информации в выходной поток сервлетов ``GameAttemptServlet`` и ``RequestServlet``.
 * Разработана игра, по угадыванию случайного числа пользователем (от 0 до 9).
   Для этого в приложении ``HomeWork11WebUI`` страница ``game.jsp``, с двумя полями для ввода логина пользователя и целого
   числа, а также с кнопкой отправки результата.
   Для привлечения интереса к процессу, предоставляется возможность ограниченного количество попыток угадывания числа
   (10 попыток), после чего доступ к игре блокируется.
 * Порядок сборки:
   * Общая, для основного ``HomeWork11Core`` и ``HomeWork11WebUI`` приложений, библиотека ``HomeWork11Lib``:
   ````
        mvn -f HomeWork11Lib/pom.xml clean package install
   ````
   * Основноe приложение ``HomeWork11Core``:
   ````
        mvn -f HomeWorkPersistent/pom.xml clean package install dependency:copy-dependencies
   ````
   * Создение схемы и хранимых процедур:
   ````
        ./createSchema.sh
        ./createFunction.sh
   ````
      *  или Windows
   ````
        createSchema.bat
        createFunction.bat
   ````
   * Развёртывание основного приложения ``HomeWork11Core``:
   ````
        mvn -f HomeWork/pom.xml glassfish:deploy
   ````
   * ``HomeWork11WebUI`` приложение:
   ````
        mvn -f HomeWork11WebUI/pom.xml clean package glassfish:deploy
   ````
