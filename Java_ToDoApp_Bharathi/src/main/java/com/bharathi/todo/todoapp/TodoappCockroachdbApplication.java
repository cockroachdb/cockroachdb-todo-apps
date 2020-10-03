/*Bharathi BMS (C) 2020*/
package com.bharathi.todo.todoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = LiquibaseAutoConfiguration.class)
public class TodoappCockroachdbApplication {

    public static void main(String[] args) {
        
        
        SpringApplication.run(TodoappCockroachdbApplication.class, args);
    }

}
