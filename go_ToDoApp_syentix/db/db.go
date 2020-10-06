// Ole S. (C) 2020

package db

import (
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

// CreateConnection creates a DB connection
func CreateConnection() *gorm.DB {
	dsn := "dbname=hacktoberfest host=localhost user=todouser port=26257 sslmode=disable"
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		panic(err)
	}

	return db
}
