// Ole S. (C) 2020

package db

import (
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

// ConnectDB is used to connect to the CockroachDB
func ConnectDB() *gorm.DB {
	dsn := "dbname=hacktoberfest port=26257 sslmode=disable"
	db, err := gorm.Open(postgres.Open(dsn), &gorm.Config{})
	if err != nil {
		panic(err)
	}
	return db
}
