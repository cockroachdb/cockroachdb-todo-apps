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
			fmt.Println("Add")
			break
		case "rm":
			fmt.Println("Remove")
		case "remove":
			fmt.Println("Remove")
		case "complete":
			fmt.Println("Check")
		case "check":
			fmt.Println("Check")
		case "list":
			fmt.Println("List")
		case "info":
			fmt.Println("Info")
		case "q":
			quit()
		case "exit":
			quit()
		case "quit":
			quit()
		case "help":
			handlers.Help()
		default:
			fmt.Println("That's not a command. Try 'help' to check out the valid commands.")
		}

		fmt.Println()
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
