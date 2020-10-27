package model

import (
	"time"

	"todo_app/internal/database"
)

func init() {
	database.DB.AutoMigrate(&Task{})
}

type Task struct {
	ID        uint
	Name      string
	Checked   bool
	CreatedAt time.Time
	UpdatedAt time.Time
}
