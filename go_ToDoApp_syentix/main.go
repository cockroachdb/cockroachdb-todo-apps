// Ole S. (C) 2020
package main

import (
	"bufio"
	"fmt"
	"go-todo-app/db"
	"go-todo-app/handlers"
	"go-todo-app/models"
	"os"
	"strconv"
	"strings"
)

func main() {

	fmt.Println("Welcome to the new and totally great Todo CLI! Use q/exit/quit to exit the program.")

	db.CreateConnection().AutoMigrate(&models.Todo{})

	for true {
		reader := bufio.NewReader(os.Stdin)

		fmt.Print(">")
		in, _ := reader.ReadString('\n')
		in = strings.Replace(in, "\n", "", -1)
		args := strings.SplitN(in, " ", 2)

		switch args[0] {
		case "add":
			if argsValid(args, false) {
				handlers.AddTodo(args[1])
			} else {
				fmt.Println("\tYou need to add text to your todo :)")
			}

		case "rm":
			if argsValid(args, true) {
				index, _ := strconv.Atoi(args[1])
				handlers.RemoveTodo(index)
			} else {
				fmt.Println("\tNot a valid id.")
			}
		case "remove":
			if argsValid(args, true) {
				index, _ := strconv.Atoi(args[1])
				handlers.RemoveTodo(index)
			} else {
				fmt.Println("Not a valid id.")
			}

		case "complete":
			if argsValid(args, true) {
				index, _ := strconv.Atoi(args[1])
				handlers.CheckTodo(index)
			} else {
				fmt.Println("Not a valid id.")
			}
		case "check":
			if argsValid(args, true) {
				index, _ := strconv.Atoi(args[1])
				handlers.CheckTodo(index)
			} else {
				fmt.Println("Not a valid id.")
			}

		case "list":
			handlers.GetAllTodos()

		case "q":
			quit()
		case "exit":
			quit()
		case "quit":
			quit()

		case "help":
			handlers.Help()
		default:
			fmt.Println("\tThat's not a command. Try 'help' to check out the valid commands.")
		}
	}

}

func argsValid(args []string, checkIfID bool) bool {
	if checkIfID == false {
		return true
	}
	if _, err := strconv.Atoi(args[1]); err != nil {
		return false
	}
	return true
}

func quit() {
	fmt.Println("Cya next time!")
	os.Exit(0)
}
