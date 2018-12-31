#!/bin/sh
mvn clean package -DskipTests
mvn -f HomeWorkPersistent/pom.xml install -DskipTests
mvn -f HomeWork/pom.xml glassfish:undeploy
mvn -f HomeWork/pom.xml glassfish:deploy
