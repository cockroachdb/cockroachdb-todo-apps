/*
Copyright 2020 John Solo
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain
a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
package com.Java_ToDoApp_JohnSolo.controller;

import com.Java_ToDoApp_JohnSolo.model.ToDoItem;
import com.Java_ToDoApp_JohnSolo.service.ToDoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RequestMapping("/todos")
@RestController
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getToDoItems(){
        try {
            return new ResponseEntity<>(toDoService.getToDoItems(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("ToDoItems not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getToDoItem(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(toDoService.getToDoItem(id), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("ToDoItem resource with id: " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createToDoItem(@RequestBody ToDoItem toDoItem){
        try {
            toDoService.createToDoItem(toDoItem);
            return new ResponseEntity<>("ToDoItem successfully created with id: " + toDoItem.getId(), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("ToDoItem creation failed", HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateToDoItem(@PathVariable("id") Long id, @RequestBody ToDoItem toDoItem){
        try {
            toDoItem.setId(id);
            toDoService.updateToDoItem(toDoItem);
            return new ResponseEntity<>("ToDoItem resource with id: " + id + " successfully updated", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("ToDoItem resource with id: " + id + " cannot be updated", HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteToDoItem(@PathVariable("id") Long id){
        try {
            toDoService.deleteToDoItem(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>("Cannot delete ToDoItem resource with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
}
