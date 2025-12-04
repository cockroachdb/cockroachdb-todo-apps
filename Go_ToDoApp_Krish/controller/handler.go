package controller

import (
	"encoding/json"
	"html/template"
	"log"
	"net/http"
	"path"

	"github.com/krishanu7/cockroachdb-todo-apps/Go_ToDoApp_Krish/models"
)

type response struct {
	ID      string `json:"id,omitempty"`
	Message string `json:"message,omitempty"`
}

func HomePage(w http.ResponseWriter, r *http.Request) {
	filepath := path.Join("views", "index.html")
	temp, err := template.ParseFiles(filepath)

	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	err = temp.Execute(w, nil)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}

func GetAllTodos(w http.ResponseWriter, r *http.Request) {
	todos, err := models.GetAllTodos()
	if err != nil {
		log.Printf("Unable to get all todos list. %v", err)
		http.Error(w, "Unable to get todos", http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(todos)
}

func CreateTodo(w http.ResponseWriter, r *http.Request) {
	var todo models.Todo

	err := json.NewDecoder(r.Body).Decode(&todo)
	if err != nil {
		log.Printf("Unable to decode the request body. %v", err)
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	insertID, err := models.InsertTodo(todo.Text)
	if err != nil {
		log.Printf("Unable to insert todo. %v", err)
		http.Error(w, "Unable to create todo", http.StatusInternalServerError)
		return
	}

	res := response{
		ID:      insertID,
		Message: "Todo created successfully!",
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(res)
}

func DeleteTodo(w http.ResponseWriter, r *http.Request) {
	var todo models.Todo
	err := json.NewDecoder(r.Body).Decode(&todo)
	if err != nil {
		log.Printf("Unable to decode the request body. %v", err)
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	err = models.DeleteTodo(todo.ID)
	if err != nil {
		log.Printf("Unable to delete todo. %v", err)
		http.Error(w, "Unable to delete todo", http.StatusInternalServerError)
		return
	}

	res := response{
		Message: "Todo deleted successfully!",
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(res)
}

func UpdateTodo(w http.ResponseWriter, r *http.Request) {
	var todo models.Todo
	err := json.NewDecoder(r.Body).Decode(&todo)
	if err != nil {
		log.Printf("Unable to decode the request body. %v", err)
		http.Error(w, "Invalid request body", http.StatusBadRequest)
		return
	}

	err = models.UpdateTodo(todo.ID, todo.Checked)
	if err != nil {
		log.Printf("Unable to update todo. %v", err)
		http.Error(w, "Unable to update todo", http.StatusInternalServerError)
		return
	}

	res := response{
		Message: "Todo updated successfully!",
	}
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(res)
}
