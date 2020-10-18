//
//  ToDoViewModel.swift
//  ToDo
//
//  Created by Tim Brooks on 10/16/20.
//
/*
 Copyright 2020 Tim Brooks

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

import Foundation
import Combine

class ToDoViewModel: ObservableObject {

    // MARK: - Enum
    enum Section {
        case open
        case complete
    }

    // MARK: - Properties
    private let repository: Repository
    @Published private(set) var completeTodos: [ToDo] = []
    @Published private(set) var openTodos: [ToDo] = []
    @Published private(set) var currentError: Error?
    var showError: Bool {
        return (self.currentError != nil)
    }

    var showEmpty: Bool {
        return !showError && (completeTodos.count == 0) && (openTodos.count == 0)
    }

    // MARK: - Initializers
    init(repository: Repository) {
        self.repository = repository
        connect()
    }
    
    func connect() {
        self.repository.connect { [weak self] error in
            self?.currentError = error
        }
    }
    
    func loadToDos() {
        
        guard self.currentError == nil else {
            return
        }
        
        repository.fetchToDos { [weak self] result in
            
            switch result {
                case .success(let todos):
                    let sortedToDos = todos.sorted(by: { $0.created < $1.created} )
                    self?.openTodos = sortedToDos.filter  { !$0.complete }
                    self?.completeTodos = sortedToDos.filter { $0.complete }
                case .failure(let error):
                    self?.currentError = error
            }            
        }
    }
    
    func add(todo: ToDo) {
        repository.add(todo: todo) { [weak self] result in
            self?.refreshOn(result: result)
        }
    }

    func update(todo: ToDo) {
        repository.update(todo: todo) { [weak self] result in
            self?.refreshOn(result: result)
        }
    }

    func deleteToDo(todo: ToDo) {
        repository.delete(todo: todo) { [weak self] result in
            self?.refreshOn(result: result)
        }
    }

    func deleteToDos(ids: [UUID]) {
        repository.delete(ids: ids) { [weak self] result in
            self?.refreshOn(result: result)
        }
    }

    func disconnect() {
        repository.disconnect()
    }
    
    func delete(at offsets: IndexSet, section: Section) {
        
        let todoIds: [UUID]
        
        switch section {
            case .open:
                todoIds = offsets.map( { self.openTodos[$0] } ).map { $0.id }
            case .complete:
                todoIds = offsets.map( { self.completeTodos[$0] } ).map { $0.id }
        }
        
        deleteToDos(ids: todoIds)
    }
    
    func upsert(todo: EditingToDo) {
        if !todo.wasMutated {
            return
        }
        
        let isNew = (todo.id == nil)
        let actualToDo = ToDo(id: todo.id ?? UUID(),
                              description: todo.description,
                              complete: todo.complete,
                              created: todo.created)
        // Save or insert
        if isNew {
            add(todo: actualToDo)
        } else {
            update(todo: actualToDo)
        }
    }
    
    private func refreshOn(result: Result<Bool, Error>) {
        
        switch result {
            case .failure(let error):
                self.currentError = error
            case .success(_):
                self.loadToDos()
        }
    }
}
