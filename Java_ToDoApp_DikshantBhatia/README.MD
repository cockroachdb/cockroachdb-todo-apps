# TodoApp
A sample todo app built in Java that uses CockroachDB for storing data.

## Prerequisites
- OpenJDK 11
- Maven
- CockroachDB

## Install on Local Environment

- Start a local single node CockroachDB cluster: ```cockroach start-single-node --insecure``` 
(https://www.cockroachlabs.com/docs/v20.1/cockroach-start-single-node).
- Enter SQL shell:
```cockroach sql --insecure```
- Create a user:
```
create user if not exists dikshantroach;
grant all on database defaultdb to dikshantroach;
````
- Update the db connection url and username in application.properties.
- Update the server.port value in application.properties to your desired port.
 
## Running the Application

- ```mvn spring-boot:run```

  Note: A table in DB will be recreated everytime whenever we start the application.
- Go to http://localhost:8081/swagger-ui.html 

  Note: replace 8081 with the port you specified in application.properties
- You should now be able to view the api docs and perform crud operations. 
 
