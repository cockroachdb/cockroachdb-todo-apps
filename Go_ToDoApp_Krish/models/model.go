package models

import (
	"database/sql"
	"fmt"
	"log"

	_ "github.com/lib/pq"
)

var db *sql.DB

type Todo struct {
	ID      string `json:"id"`
	Text    string `json:"text"`
	Checked bool   `json:"checked"`
}

func InitDB(dataSourceName string) {
	var err error
	db, err = sql.Open("postgres", dataSourceName)
	if err != nil {
		log.Panic(err)
	}

	if err = db.Ping(); err != nil {
		log.Panic(err)
	}
	fmt.Println("Database connection established!")
}

func GetAllTodos() ([]Todo, error) {
	rows, err := db.Query("SELECT id, text, checked FROM todos")
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	var todos []Todo
	for rows.Next() {
		var t Todo
		if err := rows.Scan(&t.ID, &t.Text, &t.Checked); err != nil {
			return nil, err
		}
		todos = append(todos, t)
	}
	return todos, nil
}

func InsertTodo(text string) (string, error) {
	var id string
	err := db.QueryRow("INSERT INTO todos (text, checked) VALUES ($1, $2) RETURNING id", text, false).Scan(&id)
	if err != nil {
		return "", err
	}
	return id, nil
}

func DeleteTodo(id string) error {
	_, err := db.Exec("DELETE FROM todos WHERE id = $1", id)
	return err
}

func UpdateTodo(id string, checked bool) error {
	_, err := db.Exec("UPDATE todos SET checked = $1 WHERE id = $2", checked, id)
	return err
}
