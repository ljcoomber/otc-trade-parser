#!/bin/sh
mvn install:install-file \
  -Dfile=lib/jTrolog2_1.jar \
  -DgroupId=net.java.dev \
  -DartifactId=jtrolog \
  -Dversion=2.1 \
  -Dpackaging=jar 