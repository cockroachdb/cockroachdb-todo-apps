/*Bharathi BMS (C) 2020*/
package com.bharathi.todo.todoapp.service;

import com.bharathi.todo.todoapp.model.Todo;
import com.bharathi.todo.todoapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public Todo addTodoNote(Todo note) {

        note.setCreatedOn(new Timestamp(System.currentTimeMillis()));
        return todoRepository.save(note);
    }

    @Override
    public Boolean updateTodoNote(Todo note) {

        Optional<Todo> data = todoRepository.findById(note.getId());
        if (data.isPresent()) {

            data.get().setData(note.getData());
            Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
            data.get().setCreatedOn(currentTimeStamp);
            todoRepository.save(data.get());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean deleteTodoNote(Long id) {

        if (todoRepository.findById(id).isPresent()) {
            todoRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Todo> getAllTodoNote() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<Todo> getTodoNote(Long id) {
        return todoRepository.findById(id);
    }
}
