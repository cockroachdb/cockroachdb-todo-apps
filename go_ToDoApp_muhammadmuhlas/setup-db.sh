#!/bin/sh
set -ex

USERNAME="hack"

cockroach sql -e "DROP TABLE IF EXISTS todos;" --insecure
cockroach sql -e "DROP DATABASE IF EXISTS hacktoberfest;" --insecure
cockroach sql -e "CREATE DATABASE hacktoberfest;" --insecure
cockroach sql -e "CREATE USER IF NOT EXISTS $USERNAME;" --insecure
cockroach sql -e "GRANT ALL ON DATABASE hacktoberfest TO $USERNAME;" --insecure