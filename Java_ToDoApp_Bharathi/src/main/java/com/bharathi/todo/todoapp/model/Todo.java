/*Bharathi BMS (C) 2020*/
package com.bharathi.todo.todoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "todo")
@Getter
@Setter
@NoArgsConstructor
public class Todo implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String data;

    @Column(name = "created_on")
    private Timestamp createdOn;

    public Todo(final Long id) {
        this.id = id;
    }
}
