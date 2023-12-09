package routes

import "github.com/gin-gonic/gin"

func SetRoutes() *gin.Engine {
	router := gin.Default()
	router.GET("")
	router.GET("")
	router.PUT("")
	router.DELETE("")
	router.POST("")
	return router
}
