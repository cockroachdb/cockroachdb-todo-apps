package utilities

import (
	"database/sql"
	"os"

	_ "github.com/lib/pq"
	"github.com/rs/zerolog"
)

var Logger zerolog.Logger = zerolog.New(os.Stdout)

func DbConn() (*sql.DB, error) {
	cockroachURL := "postgresql://root@localhost:26257/hacktoberfest?sslmode=disable"

	db, err := sql.Open("postgres", cockroachURL)
	if err != nil {
		return nil, err
	}
	if err := db.Ping(); err != nil {
		return nil, err
	}
	return db, nil
}
