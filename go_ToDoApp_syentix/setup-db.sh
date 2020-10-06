#!/bin/sh
set -ex

if [ -z $1 ]
then
USERNAME="todouser"
else 
USERNAME=$1
fi

if [ -z $2 ]
then
PASSWORD="password"
else 
PASSWORD=$2
fi

#cockroach sql -e "CREATE USER $USERNAME WITH PASSWORD $PASSWORD;" --insecure
cockroach sql -e "DROP TABLE IF EXISTS todos;" --insecure
cockroach sql -e "DROP DATABASE IF EXISTS hacktoberfest;" --insecure
cockroach sql -e "CREATE DATABASE hacktoberfest;" --insecure
#cockroach sql -e "GRANT ALL ON DATABASE hacktoberfest TO $USERNAME;" --insecure
cockroach -d hacktoberfest sql -e "CREATE TABLE IF NOT EXISTS todos(id UUID NOT NULL DEFAULT gen_random_uuid(), todo_text VARCHAR(255), checked BOOLEAN, created TIMESTAMP DEFAULT now());" --insecure



