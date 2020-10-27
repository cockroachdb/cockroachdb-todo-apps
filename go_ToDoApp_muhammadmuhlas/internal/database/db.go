package database

import (
	"gorm.io/gorm"
	"time"
	"todo_app/internal/database/cockroach"
)

var DB *gorm.DB

func init() {
	config := gorm.Config{}
	DB = cockroach.CreateConnection(&config)

	db, err := DB.DB()
	if err != nil {
		panic(err)
	}

	db.SetMaxIdleConns(5)
	db.SetMaxOpenConns(5)
	db.SetConnMaxLifetime(1 * time.Minute)
}
