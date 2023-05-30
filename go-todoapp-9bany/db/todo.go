package db

import "time"

type TodoItem struct {
	Id         int       `json:"id"`
	Title      string    `json:"title"`
	CreatedAt  time.Time `json:"create_at"`
	CompleteAt time.Time `json:"complete_at"`
}
