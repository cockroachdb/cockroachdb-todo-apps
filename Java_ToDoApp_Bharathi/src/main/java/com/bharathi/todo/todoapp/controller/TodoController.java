/*Bharathi BMS (C) 2020*/
package com.bharathi.todo.todoapp.controller;

import com.bharathi.todo.todoapp.model.Todo;
import com.bharathi.todo.todoapp.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/todo")
@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping("/create")
    public ResponseEntity<String> addTodoNote(@RequestBody Todo todo) {

        Long id = todoService.addTodoNote(todo).getId();
        return new ResponseEntity<>("Todo note added successfully with Id: " + id, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTodoNote(@PathVariable("id") Long id, @RequestBody Todo todo) {

        todo.setId(id);
        if (todoService.updateTodoNote(todo))
            return new ResponseEntity<>("Todo note updated successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Todo Note with Id: " + id + " not found", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTodoNote(@PathVariable("id") Long id) {

        if (todoService.deleteTodoNote(id))
            return new ResponseEntity<>("Todo note deleted successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Invalid Todo Note Id", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Todo>> getAllTodoNote() {

        return new ResponseEntity<>(todoService.getAllTodoNote(), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Todo> addTodoNote(@PathVariable("id") Long id) {
        Optional<Todo> todo = todoService.getTodoNote(id);
        if (todo.isPresent())
            return new ResponseEntity<>(todo.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

    }


}
