// Haidar Rifki (C) 2020
package models

// Todo schema of the todos table
type Todo struct {
	ID      string `json:"id"`
	Text    string `json:"text"`
	Checked bool   `json:"checked"`
}
