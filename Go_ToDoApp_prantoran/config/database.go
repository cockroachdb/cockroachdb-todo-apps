/*
Important note

Add the following header in a comment at the beginning of each source file, and fill in your name and the year.

Copyright [2020] [prantoran]

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

*/

package config

import (
	"fmt"

	"github.com/jinzhu/gorm"
)

var DB *gorm.DB

type DBConfig struct {
	Host     string
	Port     int
	User     string
	DBName   string
	Password string
	SSLMode	 string
}


func BuildDBConfig() *DBConfig {
	dbConfig := DBConfig{
		Host:     "localhost",
		Port:     26257,
		User:     "admin",
		DBName:   "todos",
		SSLMode:  "disable",
	}
	return &dbConfig
}


func DbURL(dbConfig *DBConfig) string {
	return fmt.Sprintf(
		"postgresql://%s@%s:%d/%s?sslmode=%s",
		dbConfig.User,
		dbConfig.Host,
		dbConfig.Port,
		dbConfig.DBName,
		dbConfig.SSLMode,
	)
}
