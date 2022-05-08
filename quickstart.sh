#!/bin/bash
mvn clean & mvn compile & mvn package
docker-compose up
