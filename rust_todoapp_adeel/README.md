# Rust TodoApp

## Install CockroachDb
### Create a user todoapp and database todolists and grant it everything

`CREATE USER IF NOT EXISTS todoapp;`

`CREATE DATABASE todolist;`

`GRANT ALL ON DATABASE todolists to todoapp;`

### To build the app

`cargo build`

### To run the app 

`cd target`

`.\rust_todoapp_adeel`


Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
