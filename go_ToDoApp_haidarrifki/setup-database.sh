#!/bin/sh

set -ex

cockroach sql -e 'DROP DATABASE IF EXISTS hacktoberfest'
cockroach sql -e 'CREATE DATABASE hacktoberfest'
cockroach sql -e 'GRANT ALL ON DATABASE hacktoberfest TO example'
cockroach sql -e 'CREATE TABLE IF NOT EXISTS todos (id UUID NOT NULL DEFAULT gen_random_uuid(), text TEXT, checked BOOLEAN)'