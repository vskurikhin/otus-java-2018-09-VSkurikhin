#!/bin/sh
mvn -f HomeWork11Lib/pom.xml clean package install
# First time:
# mvn -f HomeWorkPersistent/pom.xml clean package install dependency:copy-dependencies
# ./createSchema.sh or createSchema.bat
# ./createFunction.sh or createFunction.bat
mvn clean package install
mvn -f HomeWork11WebUI/pom.xml clean package
# Fitst time
# mvn -f HomeWork/pom.xml glassfish:deploy
# mvn -f HomeWork11WebUI/pom.xml glassfish:deploy
mvn -f HomeWork/pom.xml glassfish:redeploy
mvn -f HomeWork11WebUI/pom.xml glassfish:redeploy
