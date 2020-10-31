## Important note

Add the following header in a comment at the beginning of each source
file, and fill in your name and the year.

   Copyright [2020] [prantoran]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.



ejs
- handles view
body-parser
- middleware for parsing request body


Setup CockRoachDB using docker
1. Install Docker
2. execute `./setup-database.sh install` or sequentially run the commands in that scripts which are acquired from the [Cockroach installation blog](https://www.cockroachlabs.com/docs/v20.1/start-a-local-cluster-in-docker-linux).


CockRoachDB sql console:
    docker exec -it roach1 ./cockroach sql --insecure
    Connecting to a specific db:
        docker exec -it roach1 ./cockroach sql --insecure --database=nodejstodo


setup db items:

CREATE DATABASE nodejstodo;

CREATE TABLE todo(
id SERIAL PRIMARY KEY NOT NULL,
task text UNIQUE,
status INTEGER DEFAULT 0
);

SHOW TABLES;
SELECT id, task, status from todos;


Route:
/ renders (select *)
/addTask creates a new todo
/moveTaskDone updates todo.status 
