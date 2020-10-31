Important note

Add the following header in a comment at the beginning of each source file, and fill in your name and the year.

Copyright [2020] [prantoran]

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.


## The application listens to the following routes using a gin server:
- GET    /v1/todo                  Get todos
- POST   /v1/todo                  Create a todo
- GET    /v1/todo/:id              Get a todo
- PUT    /v1/todo/:id              Update a todo
- DELETE /v1/todo/:id              Delete a todo


Setup CockRoachDB using docker
1. Install Docker
2. execute `./setup-database.sh install` or sequentially run the commands in that scripts which are acquired from the [Cockroach installation blog](https://www.cockroachlabs.com/docs/v20.1/start-a-local-cluster-in-docker-linux).


CockRoachDB sql console:
`docker exec -it roach1 ./cockroach sql --insecure`
Connecting to a specific db:
`docker exec -it roach1 ./cockroach sql --insecure --database=todos`


setup db items:
- connect to CockroachDB (in this case, Docker instances)
`docker exec -it roach1 ./cockroach sql --insecure`
- create database
`CREATE DATABASE todos;`



`go mod init Go_ToDoApp_prantoran`
`go mod tidy`
- setups table in CockRoachDB, tests db connection and listens at port 5000 for todo queries.
`go run main.go`

If does not run then:
- delete `go.sum`, `go.mod`
- execute again `go mod init Go_ToDoApp_prantoran` & `go run main.go`
