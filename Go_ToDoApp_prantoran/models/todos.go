/*
Important note

Add the following header in a comment at the beginning of each source file, and fill in your name and the year.

Copyright [2020] [prantoran]

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

*/

package models

import (
	"time"
	"fmt"
	
	"Go_ToDoApp_prantoran/config"
	_ "github.com/jinzhu/gorm/dialects/postgres"
)

//fetch all todos at once
func GetAllTodos(todo *[]Todo) (err error) {
	if err = config.DB.Find(todo).Error; err != nil {
		return err
	}
	return nil
}

//insert a todo
func CreateATodo(todo *Todo) (err error) {
	if err = config.DB.Create(todo).Error; err != nil {
		return err
	}
	return nil
}

//fetch one todo
func GetATodo(todo *Todo, id string) (err error) {
	if err := config.DB.Where("id = ?", id).First(todo).Error; err != nil {
		return err
	}
	return nil
}

//update a todo
func UpdateATodo(todo *Todo, id string) (err error) {
	fmt.Println(todo)
	config.DB.Save(todo)
	return nil
}

//delete a todo
func DeleteATodo(todo *Todo, id string) (err error) {
	config.DB.Where("id = ?", id).Delete(todo)
	return nil
}


func PrintTodos() {
    var todos []Todo
    config.DB.Find(&todos)
    fmt.Printf("Todo at '%s':\n", time.Now())
    for _, todo := range todos {
        fmt.Printf("%d %s\n", todo.ID, todo.Title)
    }
}

func DeleteTodos() error {
    // Used to tear down the accounts table so we can re-run this
    // program.
    err := config.DB.Exec("DELETE from todo where ID > 0").Error
    if err != nil {
        return err
    }
    return nil
}