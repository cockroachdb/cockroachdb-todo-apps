package routes

import (
	"Go_ToDoApp_Gargi/handlers"

	"github.com/gin-gonic/gin"
)

func SetRoutes() *gin.Engine {
	router := gin.Default()
	todos := router.Group("/todos")
	todos.GET("/", handlers.GetAllTodoItems)
	todos.PATCH("/:id", handlers.CheckTodoItem)
	todos.PUT("/:id", handlers.UpdateTodoDescription)
	todos.DELETE("/:id", handlers.DeleteTodoItem)
	todos.POST("", handlers.AddTodoItems)
	return router
}
