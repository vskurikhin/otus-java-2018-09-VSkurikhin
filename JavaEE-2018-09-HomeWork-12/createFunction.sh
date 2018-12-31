#!/bin/sh
# Build:
#       mvn clean compile dependency:copy-dependencies
export LANG=ru_RU.UTF-8
CS="encoding=UTF-8"  
CPTARGET="./HomeWorkPersistent/target"
CP="-cp $CPTARGET/classes:$CPTARGET/dependency/*:$CPTARGET/*"
JAVA_OPTS="$CP -Dfile.$CS -Dsun.stdout.$CS -Dsun.$CS"
java $JAVA_OPTS ru.otus.CreateFunction
