// Ole S. (C) 2020

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

// GetAllTodos Gets all Todos in the Database.
func GetAllTodos() {

	var todos []models.Todo
	res := db.CreateConnection().Find(&todos)

	if res.Error != nil {
		panic(res.Error)
	}

	if len(todos) == 0 {
		fmt.Println("No todos found.")
	} else {
		for i, todo := range todos {
			fmt.Printf("%d) %s", i, todo.Text)
		}
	}
}

// Help prints out all valid commands and their meaning
func Help() {

	commands := [][]string{
		[]string{"add <text>", "Add a todo."},
		[]string{"rm/remove <id>", "Remove a todo with given id."},
		[]string{"complete/check <id>", "Checks a todo and marks it as completed."},
		[]string{"list", "List all todos."},
		[]string{"info <id>", "Print details about a todo."},
		[]string{"help", "Prints list of valid commands."},
	}

	for _, command := range commands {
		fmt.Printf("\t%s  -  %s\n", command[0], command[1])
	}
}
