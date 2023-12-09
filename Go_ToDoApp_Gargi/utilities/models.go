package utilities

type Todo struct {
	Id          string `json:"id"`
	Description string `json:"description"`
	Checked     bool   `json:"checked"`
}
