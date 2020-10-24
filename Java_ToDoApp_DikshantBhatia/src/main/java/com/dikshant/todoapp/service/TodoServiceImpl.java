package com.dikshant.todoapp.service;

import com.dikshant.todoapp.exception.TodoNotFoundException;
import com.dikshant.todoapp.model.Todo;
import com.dikshant.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    TodoRepository todoRepository;

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    @Override
    public Integer addTodo(Todo todo) {
        // ensuring to not use the id if added by manipulating the request
        todo.setId(null);
        return todoRepository.save(todo).getId();
    }

    @Override
    public void updateTodo(Integer id, Todo todo) {
      todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
      todo.setId(id);
      todoRepository.save(todo);
    }

    @Override
    public void deleteTodo(Integer id) {
      todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
      todoRepository.deleteById(id);
    }

    @Override
    public Todo findById(Integer id) {
        return todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found with id: " + id));
    }
}
