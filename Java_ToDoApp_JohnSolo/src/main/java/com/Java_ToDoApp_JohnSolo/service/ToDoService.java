/*
Copyright 2020 John Solo
Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain
a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
*/
package com.Java_ToDoApp_JohnSolo.service;

import com.Java_ToDoApp_JohnSolo.model.ToDoItem;
import com.Java_ToDoApp_JohnSolo.respository.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToDoService {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    public List<ToDoItem> getToDoItems(){
        return toDoItemRepository.findAll();
    }

    public ToDoItem getToDoItem(Long id){
        return toDoItemRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("ToDoItem resource with id: " + id + " not found"));
    }

    public ToDoItem createToDoItem(ToDoItem toDoItem){
        return toDoItemRepository.save(toDoItem);
    }

    public void updateToDoItem(ToDoItem toDoItem){
        Optional<ToDoItem> td = toDoItemRepository.findById(toDoItem.getId());
        td.get().setTitle(toDoItem.getTitle());
        td.get().setDescription(toDoItem.getDescription());
        td.get().setCompleted(toDoItem.getCompleted());
        toDoItemRepository.save(td.get());
    }

    public void deleteToDoItem(long id){
        toDoItemRepository.deleteById(id);
    }
}
