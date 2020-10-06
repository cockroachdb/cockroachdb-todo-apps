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

	fmt.Println("\tTodo: '" + text + "' created.")
}

// GetAllTodos Gets all Todos in the Database.
func GetAllTodos() {

	fmt.Println("\tYour Todo's:")
	fmt.Println("\t------------")

	var todos []models.Todo
	res := db.CreateConnection().Find(&todos)

	if res.Error != nil {
		panic(res.Error)
	}

	if len(todos) == 0 {
		fmt.Println("No todos found.")
	} else {
		for i, todo := range todos {
			if todo.Checked {
				fmt.Printf("\t%d) %s\t✅\n", i, todo.Text)
			} else {
				fmt.Printf("\t%d) %s\n", i, todo.Text)
			}
		}
	}
}

// Help prints out all valid commands and their meaning
func Help() {

	fmt.Println("\tList of valid commands:")
	fmt.Println("\t-----------------------")

	commands := [][]string{
		[]string{"add <text>         ", "Add a todo."},
		[]string{"rm/remove <id>     ", "Remove a todo with given id."},
		[]string{"complete/check <id>", "Checks a todo and marks it as completed."},
		[]string{"list               ", "List all todos."},
		[]string{"help               ", "Prints list of valid commands."},
		[]string{"q/quit/exit        ", "Quit the program."},
	}

	for _, command := range commands {
		fmt.Printf("\t%s  -  %s\n", command[0], command[1])
	}
}

// RemoveTodo removes a Todo from the Database
func RemoveTodo(index int) {
	DB := db.CreateConnection()

	var todos []models.Todo
	res := DB.Find(&todos)

	if res.Error != nil {
		panic(res.Error)
	}

	if index < 0 || index > len(todos) {
		fmt.Println("\tInvalid Index.")
		return
	}

	res = DB.Delete(&todos[index])
	if res.Error != nil {
		panic(res.Error)
	}

	fmt.Println("\tDeleted.")
}

// CheckTodo marks the todo as completed
func CheckTodo(index int) {
	DB := db.CreateConnection()

	var todos []models.Todo
	res := DB.Find(&todos)

	if res.Error != nil {
		panic(res.Error)
	}

	if index < 0 || index > len(todos) {
		fmt.Println("\tInvalid Index.")
		return
	}

	todos[index].Checked = true
	res = DB.Save(&todos)
	if res.Error != nil {
		panic(res.Error)
	} else {
		fmt.Println("\tGood job! 👍")
	}
}
