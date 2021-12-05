#!/bin/sh
echo "wait DB container up"
dockerize -wait tcp://coupon-db:3306 -timeout 20s

echo "######################## run application ########################"
java -jar \
  -Dspring.profiles.active=${PROFILE_OPTION} \
  ${SRC_NAME}.jar
