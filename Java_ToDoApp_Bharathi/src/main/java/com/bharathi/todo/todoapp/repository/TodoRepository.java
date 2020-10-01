/*Bharathi BMS (C) 2020*/
package com.bharathi.todo.todoapp.repository;

import com.bharathi.todo.todoapp.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long> {
}
