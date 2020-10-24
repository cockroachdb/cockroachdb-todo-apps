package com.dikshant.todoapp.exception;

import lombok.Getter;

@Getter
public class TodoNotFoundException extends RuntimeException {

    private String message;

    public TodoNotFoundException(String message){
        this.message = message;
    }
}
