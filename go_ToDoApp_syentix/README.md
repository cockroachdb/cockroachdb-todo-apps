# Golang Command Line Todo App

I created a Todo App for the Command line with golang.

## What do I need?

1. [Golang](https://golang.org/doc/install) should be installed correctly.
2. [CockroachDB](https://www.cockroachlabs.com/docs/v20.1/install-cockroachdb-mac.html) installed with nodes setup (Using insecure mode)

## Setup

First clone the repo and move into this folder.
After having all nodes set up correctly:

`chmod +x setup-db.sh && ./setup-db.sh`

This will create a `hacktoberfest` database and create a user that will be used to connect to the database.

`go run main.go`

Will run the Todo-App. Type `help` to get more information on what commands are available.
