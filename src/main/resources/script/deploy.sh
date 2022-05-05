#!/bin/bash

if [ -z $1 ]; then
        echo "you must input a port"
        exit 0
fi

if [ -z $2 ]; then
        echo "you must input a version"
fi
echo "port="$1
echo "version="$2
echo "profile"=$3
PID=$(lsof -i:$1 | awk 'NR == 2 {print $2}')
nohup java -jar ~/springbootdemo/wow-backend-$2-SNAPSHOT.jar --spring.profiles.active=$3 > nohup.log 2>&1 &