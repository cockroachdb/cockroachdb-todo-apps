# Java TodoApp with CockroachDB and no frameworks \o/

Yes, it is a pure Java app with only CockroachDB driver

# Quickstart

## Fire up your database:

``` 
docker network create -d bridge roachnet
docker rm roach1
docker run --name roach1 --hostname=roach1 --net=roachnet -p 26257:26257 -p 8080:8080 -v "${PWD}/cockroach-data/roach1:/cockroach/cockroach-data" cockroachdb/cockroach:v20.1.8 start 
--insecure
```  

## Initial setup

```
docker exec -it roach1 ./cockroach sql --insecure
CREATE USER IF NOT EXISTS maxroach;
CREATE DATABASE todo;
USE todo;
create table todo(description varchar(100));
GRANT ALL ON DATABASE todo TO maxroach;
```
 
## Build

```
mvn clean package
```

## Running 

```
java -jar target/todoapp-jar-with-dependencies.jar
```

## Expected result


```
======== CockroachDB TodoApps with Pure Java ======== 
== CLEAN TABLE ==

com.cockroachlabs.todoapps.ToDoDAO.removeAll:
    'DELETE FROM todo'
== INSERT ==

com.cockroachlabs.todoapps.ToDoDAO.newItem:
    'INSERT INTO todo(description) VALUES ('Learn Java')'

com.cockroachlabs.todoapps.ToDoDAO.newItem:
    'INSERT INTO todo(description) VALUES ('Learn CockroachDB')'
== LIST ==
TodoItem [ description=Learn Java ]
TodoItem [ description=Learn CockroachDB ]
== REMOVE ==

com.cockroachlabs.todoapps.ToDoDAO.removeItem:
    'DELETE FROM todo WHERE description='Learn Java''
==  NEW LIST ==
TodoItem [ description=Learn CockroachDB ]

```
