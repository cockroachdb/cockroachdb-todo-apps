package main

import (
	"Go_ToDoApp_Gargi/routes"

	"github.com/rs/zerolog"
)

func main() {
	zerolog.SetGlobalLevel(zerolog.InfoLevel)
	//set up routes
	routes.SetRoutes().Run(":8081")
}
