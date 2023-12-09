package handlers

import (
	"Go_ToDoApp_Gargi/utilities"
	"net/http"

	"github.com/gin-gonic/gin"
)

func CheckTodoItem(ctx *gin.Context) {
	db, err := utilities.DbConn()
	defer db.Close()
	if err != nil {
		utilities.Logger.Error().Msgf("Error Connecting to db. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error Connecting to db.")
		return
	}
	utilities.Logger.Info().Msgf("Successfully connection to db!!!")
	id := ctx.Query("id")
	if err != nil {
		utilities.Logger.Error().Msgf("Error converting id to int. %s", err.Error())
		ctx.JSON(http.StatusBadRequest, "Invalid ID format")
		return
	}
	_, err = db.Exec("UPDATE hacktoberfest.todos SET checked = $1 WHERE id = $2", true, id)
	if err != nil {
		utilities.Logger.Error().Msgf("Error preparing db query. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error preparing db query.")
		return
	}
	ctx.JSON(http.StatusOK, "Successfully checked item into todo list.")
}

func GetAllTodoItems(ctx *gin.Context) {
	db, err := utilities.DbConn()
	defer db.Close()
	if err != nil {
		utilities.Logger.Error().Msgf("Error Connecting to db. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error Connecting to db.")
		return
	}
	utilities.Logger.Info().Msgf("Successfully connection to db!!!")
	todoList := []utilities.Todo{}
	todos := utilities.Todo{}
	rows, err := db.Query("SELECT * FROM todos")
	if err != nil {
		utilities.Logger.Error().Msgf("Error querying data from db. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error querying data from db.")
		return
	}
	for rows.Next() {
		if err := rows.Scan(&todos.Id, &todos.Description, &todos.Checked); err != nil {
			utilities.Logger.Error().Msgf("Error fetching rows from table. %s", err.Error())
			ctx.JSON(http.StatusInternalServerError, "Error fetching rows from table.")
			return
		}
		todoList = append(todoList, todos)
	}
	ctx.JSON(http.StatusOK, todoList)
}

func AddTodoItems(ctx *gin.Context) {
	db, err := utilities.DbConn()
	defer db.Close()
	if err != nil {
		utilities.Logger.Error().Msgf("Error Connecting to db. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error Connecting to db.")
		return
	}
	utilities.Logger.Info().Msgf("Successfully connection to db!!!")
	req := utilities.Todo{}
	if err := ctx.BindJSON(&req); err != nil {
		utilities.Logger.Error().Msgf("Error binding request object. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error binding request object.")
		return
	}
	_, err = db.Exec("INSERT INTO todos (description,checked) VALUES ($1, $2)", req.Description, req.Checked)
	if err != nil {
		utilities.Logger.Error().Msgf("Error inserting data into db. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error inserting data into db.")
		return
	}
	ctx.JSON(http.StatusOK, "Successfully registered item in the todo list.")
}

func UpdateTodoDescription(ctx *gin.Context) {
	db, err := utilities.DbConn()
	defer db.Close()
	if err != nil {
		utilities.Logger.Error().Msgf("Error Connecting to db. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error Connecting to db.")
		return
	}
	utilities.Logger.Info().Msgf("Successfully connection to db!!!")
	description := ctx.Query("description")
	id := ctx.Query("id")
	_, err = db.Exec("UPDATE todos SET description = $1 WHERE id = $2", description, id)
	if err != nil {
		utilities.Logger.Error().Msgf("Error preparing db query. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error preparing db query.")
		return
	}
	ctx.JSON(http.StatusOK, "Successfully updated description into todo list.")
}

func DeleteTodoItem(ctx *gin.Context) {
	db, err := utilities.DbConn()
	defer db.Close()
	if err != nil {
		utilities.Logger.Error().Msgf("Error Connecting to db. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error Connecting to db.")
		return
	}
	utilities.Logger.Info().Msgf("Successfully connection to db!!!")
	id := ctx.Query("id")
	_, err = db.Exec("DELETE FROM todos WHERE id = $1", id)
	if err != nil {
		utilities.Logger.Error().Msgf("Error executing db query. %s", err.Error())
		ctx.JSON(http.StatusInternalServerError, "Error executing db query.")
		return
	}
	ctx.JSON(http.StatusOK, "Successfully deleted item from todo list.")
}
