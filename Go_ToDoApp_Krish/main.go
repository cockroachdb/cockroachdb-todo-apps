package main

import (
	"log"
	"net/http"
	"os"

	"github.com/joho/godotenv"
	"github.com/krishanu7/cockroachdb-todo-apps/Go_ToDoApp_Krish/controller"
	"github.com/krishanu7/cockroachdb-todo-apps/Go_ToDoApp_Krish/models"
)

func main() {
	// Load .env file
	err := godotenv.Load()
	if err != nil {
		log.Fatal("Error loading .env file")
	}

	// Initialize Database
	models.InitDB(os.Getenv("DB_URL"))

	// Routes
	http.HandleFunc("/", controller.HomePage)
	http.HandleFunc("/get-todos", controller.GetAllTodos)
	http.HandleFunc("/create-todo", controller.CreateTodo)
	http.HandleFunc("/delete-todo", controller.DeleteTodo)
	http.HandleFunc("/update-todo", controller.UpdateTodo)

	// Start Server
	port := os.Getenv("PORT")
	if port == "" {
		port = "8080"
	}
	log.Printf("Server starting on port %s...", port)
	log.Fatal(http.ListenAndServe(":"+port, nil))
}
