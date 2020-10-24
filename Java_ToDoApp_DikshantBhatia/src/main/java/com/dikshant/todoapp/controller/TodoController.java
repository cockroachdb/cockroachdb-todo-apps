package com.dikshant.todoapp.controller;

import com.dikshant.todoapp.model.Todo;
import com.dikshant.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping
    public ResponseEntity<List<Todo>> getTodos(){
        return new ResponseEntity<>(todoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Todo> getTodo(@PathVariable("id") Integer todoId){
        return new ResponseEntity<>(todoService.findById(todoId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Integer> addTodo(@RequestBody Todo todo){
        return new ResponseEntity<>(todoService.addTodo(todo), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public void updateTodo(@PathVariable("id") Integer todoId, @RequestBody Todo todo){
        todoService.updateTodo(todoId, todo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable("id") Integer todoId){
        todoService.deleteTodo(todoId);
    }


}
