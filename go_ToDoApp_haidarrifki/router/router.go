// Haidar Rifki (C) 2020
package router

import (
	"golang-todo-app/middleware"
	"net/http"

	"github.com/gorilla/mux"
)

// Router is exported and used in main.go
func Router() *mux.Router {
	router := mux.NewRouter()

	// Serving static files
	router.PathPrefix("/assets/").Handler(http.StripPrefix("/assets/", http.FileServer(http.Dir("./assets"))))

	// API Endpoint
	router.HandleFunc("/", middleware.HomePage).Methods("GET", "OPTIONS")
	router.HandleFunc("/api/todo", middleware.GetAllTodo).Methods("GET", "OPTIONS")
	router.HandleFunc("/api/todo", middleware.CreateTodo).Methods("POST", "OPTIONS")
	router.HandleFunc("/api/todo", middleware.UpdateTodo).Methods("PUT", "OPTIONS")
	router.HandleFunc("/api/todo/{id}", middleware.DeleteTodo).Methods("DELETE", "OPTIONS")

	return router
}
