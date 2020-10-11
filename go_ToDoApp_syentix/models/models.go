// Ole S. (C) 2020

package models

import (
	"time"

	"gorm.io/gorm"
)

// Todo Model for CockroachDB
type Todo struct {
	gorm.Model

	ID        uint `gorm:"primaryKey"`
	Checked   bool
	Text      string
	CreatedAt time.Time
}
