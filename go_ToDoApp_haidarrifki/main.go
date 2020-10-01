// Haidar Rifki (C) 2020
package main

import (
	"fmt"
	"golang-todo-app/router"
	"log"
	"net/http"
)

func main() {
	r := router.Router()

	fmt.Println("Starting server on the port 8000...")
	log.Fatal(http.ListenAndServe(":8000", r))
}
