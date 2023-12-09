
set -ex

cockroach sql -e 'DROP DATABASE IF EXISTS hacktoberfest'
cockroach sql -e 'CREATE DATABASE hacktoberfest'
cockroach sql -e 'CREATE TABLE IF NOT EXISTS todos (id UUID NOT NULL DEFAULT gen_random_uuid(), description TEXT, checked BOOLEAN)'