#!/bin/bash
echo "wait DB container up"
dockerize -wait tcp://coupon-db:3306 -timeout 20s

# DB Migration
echo "#################### run database migration ####################"
flyway -configFiles=/flyway/conf/flyway_table.conf -locations=filesystem:/flyway/sql/table migrate

# Seed Migration
echo "####################### insert seed data #######################"
flyway -configFiles=/flyway/conf/flyway_seed.conf -locations=filesystem:/flyway/sql/seed migrate