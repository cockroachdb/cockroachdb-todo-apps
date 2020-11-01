/*
Important note

Add the following header in a comment at the beginning of each source file, and fill in your name and the year.

Copyright [2020] [prantoran]

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

*/

package main

import (

	"fmt"
	"Go_ToDoApp_prantoran/config"
	"Go_ToDoApp_prantoran/models"
	"Go_ToDoApp_prantoran/routes"

	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/postgres"
	
	// Necessary in order to check for transaction retry error codes.
	// "github.com/lib/pq"
)

var err error

func main() {

	// Creating a connection to the database
//     const addr = "postgresql://maxroach@localhost:26257/bank?sslmode=disable"

	addr := config.DbURL(config.BuildDBConfig())
	fmt.Println("addr: ", addr)
	config.DB, err = gorm.Open("postgres", addr)

	if err != nil {
		fmt.Println("statuse: ", err)
	}

	defer config.DB.Close()

	// Set to `true` and GORM will print out all DB queries.
	config.DB.LogMode(true)

	// run the migrations: todo struct
	// Automatically create the "todos" table based on the Todo
	config.DB.AutoMigrate(&models.Todo{})


	config.DB.Create(&models.Todo{ID: 1, Title: "read ch1", Description: "read ch1"});
	config.DB.Create(&models.Todo{ID: 2, Title: "read ch2", Description: "read ch2"});

	models.PrintTodos()

	if err := models.RunTransaction(config.DB, func(*gorm.DB) error {
		return models.DeleteTodos();
	}); err != nil {
		fmt.Println(err)
	}

	//setup routes 
	r := routes.SetupRouter()

	// running
	r.Run(":5000")
}