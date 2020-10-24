package com.dikshant.todoapp.service;

import com.dikshant.todoapp.model.Todo;

import java.util.List;

public interface TodoService {

    List<Todo> findAll();

    Integer addTodo(Todo todo);

    void updateTodo(Integer id, Todo todo);

    void deleteTodo(Integer id);

    Todo findById(Integer id);
}
