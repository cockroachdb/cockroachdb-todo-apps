// Ole S. (C) 2020
package main

import (
	"fmt"
	"go-todo-app/db"
	"go-todo-app/models"
)

func main() {

	fmt.Println("Welcome to the new and totally great Todo CLI!")

	db.CreateConnection().AutoMigrate(&models.Todo{})

}
