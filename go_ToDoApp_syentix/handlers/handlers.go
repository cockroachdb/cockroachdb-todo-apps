package handlers

import (
	"fmt"
	"go-todo-app/db"
	"go-todo-app/models"
)

// AddTodo adds a Todo to the DB
func AddTodo(text string) {
	todo := models.Todo{Text: text, Checked: false}

	result := db.CreateConnection().Create(&todo)

	if result.Error != nil {
		panic(result.Error)
	}

	fmt.Println("Todo: " + text + "(" + fmt.Sprint(todo.ID) + ") created.")
}
