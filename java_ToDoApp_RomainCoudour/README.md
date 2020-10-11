## ToDo Application (Java)
### Tech

- Java 11
- Spring boot
- Gradle
- CockroachDB (PostgresSQL)
- Flyway
- JOOQ

### How to start
> Note: If you are using Cockroach Cloud, you will receive this kind of address 'username@host:port'. Keep the 'host:port' part only.

- Go to gradle.properties and fill-in the blanks with your database information
- Run this command : gradle generateJooq
- Build the project
- Run ToDoApplication.java (main, spring entry point)
