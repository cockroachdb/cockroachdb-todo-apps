package task

import (
	"todo_app/internal/database"
	"todo_app/internal/output/terminal"
	"todo_app/pkg/model"

	"fmt"
	"time"
)

func ListTasks(onlyChecked bool) {
	var t []model.Task
	prep := database.DB
	prep.Order("updated_at DESC, created_at DESC")
	if onlyChecked {
		prep.Where("checked").Find(&t)
	} else {
		prep.Find(&t)
	}
	terminal.DisplayTasks(t)
}

func AddTask(t string) (string, error) {
	if len(t) > 0 && t != "" {
		return t, database.DB.Create(&model.Task{
			Name:      t,
			CreatedAt: time.Now(),
			Checked:   false,
		}).Error
	}
	fmt.Println("empty task skipped!")
	return "", nil
}

func CheckTask(ids []int) ([]int, error) {
	return ids, database.DB.Model(&model.Task{}).Where("id in (?)", ids).Update("checked", true).Error
}

func CheckAllTasks() error {
	return database.DB.Model(&model.Task{}).Where("NOT checked").Update("checked", true).Error
}

func UncheckTask(ids []int) ([]int, error) {
	return ids, database.DB.Model(&model.Task{}).Where("id in (?)", ids).Update("checked", false).Error
}

func UncheckAllTasks() error {
	return database.DB.Model(&model.Task{}).Where("checked").Update("checked", false).Error
}

func DeleteTask(ids []int) ([]int, error) {
	var task model.Task
	return ids, database.DB.Where("id in (?)", ids).Delete(&task).Error
}

func DeleteAllTasks() error {
	var task model.Task
	return database.DB.Where("id IS NOT NULL").Delete(&task).Error
}
