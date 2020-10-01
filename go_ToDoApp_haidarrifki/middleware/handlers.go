// Haidar Rifki (C) 2020
package middleware

import (
	"database/sql"
	"encoding/json"
	"fmt"
	"golang-todo-app/models"
	"log"
	"net/http"
	"os"
	"path"
	"text/template"

	"github.com/gorilla/mux"
	"github.com/joho/godotenv"
	_ "github.com/lib/pq" // postgres golang driver
)

type response struct {
	ID      string `json:"id,omitempty"`
	Message string `json:"message,omitempty"`
}

// create connection with postgres db
func createConnection() *sql.DB {
	// load .env file
	err := godotenv.Load(".env")
	if err != nil {
		log.Fatalf("Error loading .env file.")
	}

	// Open the connection
	db, err := sql.Open("postgres", os.Getenv("DB_URL"))
	if err != nil {
		panic(err)
	}

	// check the connection
	err = db.Ping()
	if err != nil {
		panic(err)
	}

	fmt.Println("Database Connected Successfully.")

	// return the connection
	return db
}

// HomePage Handler
func HomePage(w http.ResponseWriter, r *http.Request) {
	var filepath = path.Join("views", "index.html")
	var tmpl, err = template.ParseFiles(filepath)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	err = tmpl.Execute(w, nil)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
	}
}

// GetAllTodo will return all the todos
func GetAllTodo(w http.ResponseWriter, r *http.Request) {

	// get all the todos in the db
	todos, err := getAllTodos()

	if err != nil {
		log.Fatalf("Unable to get all to-do list. %v", err)
	}

	// send all the todos as response
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(todos)
}

// CreateTodo create a todo list
func CreateTodo(w http.ResponseWriter, r *http.Request) {
	// create an empty todo of
	var todo models.Todo

	// decode the json request to todo
	err := json.NewDecoder(r.Body).Decode(&todo)
	if err != nil {
		log.Fatalf("Unable to decode the request body. %v", err)
	}

	// call insert todo function and pass the todo
	insertID := insertTodo(todo)

	// format a response object
	res := response{
		ID:      insertID,
		Message: "To-do list created successfully.",
	}

	// send response
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(res)
}

// UpdateTodo update todo's detail
func UpdateTodo(w http.ResponseWriter, r *http.Request) {
	// create an empty todo of type models.Todo
	var todo models.Todo

	// decode the json request to todo
	err := json.NewDecoder(r.Body).Decode(&todo)
	if err != nil {
		log.Fatalf("Unable to decode the request body.  %v", err)
	}

	// call update todo to update the list
	updatedRows := updateTodo(todo)

	// format the message string
	msg := fmt.Sprintf("User updated successfully. Total rows/record affected %v", updatedRows)

	// format the response message
	res := response{
		ID:      todo.ID,
		Message: msg,
	}

	// send the response
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(res)
}

// DeleteTodo delete todo's detail
func DeleteTodo(w http.ResponseWriter, r *http.Request) {

	// get the todo list id from the request params, key is "id"
	params := mux.Vars(r)
	id := params["id"]

	// call the deleteTodo
	deletedRows := deleteTodo(id)

	// format the message string
	msg := fmt.Sprintf("Todo list deleted successfully. Total rows/record affected %v", deletedRows)

	// format the reponse message
	res := response{
		ID:      id,
		Message: msg,
	}

	// send the response
	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(res)
}

//------------------------- handler functions ----------------
// get list todo from the DB
func getAllTodos() ([]models.Todo, error) {
	// create db connection
	db := createConnection()

	// close the db connection
	defer db.Close()

	var todos []models.Todo

	// create the select sql query
	sqlStatement := `SELECT * FROM todos`

	// execute the sql statement
	rows, err := db.Query(sqlStatement)
	if err != nil {
		log.Fatalf("Unable to execute the query. %v", err)
	}

	// close the statement
	defer rows.Close()

	// iterate over the rows
	for rows.Next() {
		var todo models.Todo

		// unmarshal the row object to todo
		err = rows.Scan(&todo.ID, &todo.Text, &todo.Checked)

		if err != nil {
			log.Fatalf("Unable to scan the row. %v", err)
		}

		// append the todo in the todos slice
		todos = append(todos, todo)

	}

	// return empty todo on error
	return todos, err
}

// insert one todo list in the DB
func insertTodo(todo models.Todo) string {
	// create the postgres db connection
	db := createConnection()

	// close the db connection
	defer db.Close()

	// create the insert sql query
	// returning todoid will return the id of the inserted todo
	sqlStatement := `INSERT INTO todos (text, checked) VALUES ($1, $2) RETURNING id`

	// the inserted id will store in this id
	var id string

	// execute the sql statement
	// Scan function will save the insert id in the id
	err := db.QueryRow(sqlStatement, todo.Text, todo.Checked).Scan(&id)
	if err != nil {
		log.Fatalf("Unable to execute the query. %v", err)
	}

	fmt.Printf("Inserted a single record %v", id)

	// return the inserted id
	return id
}

// update todo in the DB
func updateTodo(todo models.Todo) int64 {

	// create the db connection
	db := createConnection()

	// close the db connection
	defer db.Close()

	// create the update sql query
	sqlStatement := `UPDATE todos SET checked=$2 WHERE id=$1`

	// execute the sql statement
	res, err := db.Exec(sqlStatement, todo.ID, todo.Checked)

	if err != nil {
		log.Fatalf("Unable to execute the query. %v", err)
	}

	// check how many rows affected
	rowsAffected, err := res.RowsAffected()

	if err != nil {
		log.Fatalf("Error while checking the affected rows. %v", err)
	}

	fmt.Printf("Total rows/record affected %v", rowsAffected)

	return rowsAffected
}

// delete todo in the DB
func deleteTodo(id string) int64 {

	// create the db connection
	db := createConnection()

	// close the db connection
	defer db.Close()

	// create the delete sql query
	sqlStatement := `DELETE FROM todos WHERE id=$1`

	// execute the sql statement
	res, err := db.Exec(sqlStatement, id)

	if err != nil {
		log.Fatalf("Unable to execute the query. %v", err)
	}

	// check how many rows affected
	rowsAffected, err := res.RowsAffected()

	if err != nil {
		log.Fatalf("Error while checking the affected rows. %v", err)
	}

	fmt.Printf("Total rows/record affected %v", rowsAffected)

	return rowsAffected
}
