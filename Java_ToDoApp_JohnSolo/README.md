# Java_ToDoApp
A sample to-do app that utilizes cockroach db

## Prerequisites 
- Java
- Spring Boot
- Maven 
- CockroachDB

## Running on local machine 
1. [Start a local CockroachDB cluster](https://www.cockroachlabs.com/docs/v20.1/start-a-local-cluster).
2. Enter SQL shell
   ```
   ./cockroach sql --insecure --host=localhost:26257
   ```
   or 
   ```
   cockroach sql --insecure --host=localhost:26257
   ```
3. Run SQL Commands
   ```
   create user if not exists maxroach;
   grant all on database defaultdb to maxroach;
   create table todo(id int default unique_rowid(), title string not null, description string, completed bool not null, primary key (id));
   ```
4. Start spring boot app in the same directory as ```pom.xml```
   ```
   mvn spring-boot:run
   ```
   
## API
<table>
  <tr>
    <th>HTTP Method</th>
    <th>Route</th>
    <th>Description</th>
  </tr>
  <tr>
    <td>GET</td>
    <td>/todos</td>
    <td>Gets all ToDoItems </td>
  </tr>
  <tr>
    <td>GET</td>
    <td>/todos/{id}<id></td>
    <td>Gets a specific ToDoItem by id </td>
  </tr>
  <tr>
    <td>POST</td>
    <td>/todos</td>
    <td>Creates a new ToDoItem</td>
  </tr>
  <tr>
    <td>PUT</td>
    <td>/todos/{id}</td>
    <td>Updates a ToDoItem by id</td>
  </tr>
  <tr>
    <td>DELETE</td>
    <td>/todos/{id}</td>
    <td>Deletes a ToDoItem by id</td>
  </tr>
</table>
   
## License

   Copyright 2020 John Solo

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.


