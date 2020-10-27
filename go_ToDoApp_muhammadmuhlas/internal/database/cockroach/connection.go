package cockroach

import (
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
)

// CreateConnection creates a DB connection
func CreateConnection(config *gorm.Config) *gorm.DB {
	dsn := "dbname=hacktoberfest host=localhost user=hack port=26257 sslmode=disable"
	db, err := gorm.Open(postgres.Open(dsn), config)
	if err != nil {
		panic(err)
	}

	return db
}
