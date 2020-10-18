//
//  Repository.swift
//  ToDo
//
//  Created by Tim Brooks on 10/15/20.
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
import PostgresClientKit

class Repository: ObservableObject {
    
    // MARK: - Properties
    private let dateFormatter = ISO8601DateFormatter()
    private let settings: RepositorySettings
    var connectionPool: ConnectionPool
    private struct Tables {
        static let todos = "todos"
    }
    
    private var todosPath: String {
        return "\(self.settings.database).\(Tables.todos)"
    }
    
    // MARK: - Initializers
    init(settings: RepositorySettings, user: String = "root", password: String = "") {
        self.settings = settings
        var poolConfig = ConnectionPoolConfiguration()
        poolConfig.maximumConnections = 1
        
        var connectionConfig = ConnectionConfiguration()
        connectionConfig.host = settings.host
        connectionConfig.port = settings.port
        connectionConfig.ssl = settings.ssl
        connectionConfig.database = settings.database
        connectionConfig.user = user
        
        if password.count > 0 {
            connectionConfig.credential = .scramSHA256(password: password)
        }
        
        connectionPool = ConnectionPool(connectionPoolConfiguration: poolConfig, connectionConfiguration: connectionConfig)
    }
    
    // MARK: - db Operations
    func connect(completion: @escaping (Error?) -> Void) {
        createToDosTableIfNeeded { result in
            
            let resultError: Error?
            
            switch result {
                case .failure(let error):
                    resultError = error
                case .success(_):
                    resultError = nil
            }
            
            completion(resultError)
        }
    }
    
    func disconnect() {
        // Close current pool
        connectionPool.close()
        
        // Create new pool
        connectionPool = ConnectionPool(connectionPoolConfiguration: connectionPool.connectionPoolConfiguration, connectionConfiguration: connectionPool.connectionConfiguration)
    }
    
    private func createToDosTableIfNeeded(completion: @escaping (Result<Bool, Error>) -> Void) {
        
        connectionPool.withConnection { connectionResult in
            
            let result = Result<Bool, Error> {
                
                let connection = try connectionResult.get()
                let sql = """
                CREATE TABLE IF NOT EXISTS \(self.todosPath) \
                (id STRING PRIMARY KEY, description STRING NOT NULL, complete BOOL NOT NULL, created STRING NOT NULL);
                """
                
                let statment = try connection.prepareStatement(text: sql)
                defer { statment.close() }
                try statment.execute()
                
                return true
            }
            
            completion(result)
        }
    }
    
    func fetchToDos(completion: @escaping (Result<[ToDo], Error>) -> Void) {
        let formatter = self.dateFormatter
        
        connectionPool.withConnection { connectionResult in
            
            let result = Result<[ToDo], Error> {
                
                let connection = try connectionResult.get()
                let sql = "SELECT * FROM \(self.todosPath);"
                let statment = try connection.prepareStatement(text: sql)
                defer { statment.close() }
                
                let cursor = try statment.execute()
                defer { cursor.close() }
                
                var todos = [ToDo]()
                
                for row in cursor {
                    let columns = try row.get().columns
                    let id = try columns[0].string()
                    let description = try columns[1].string()
                    let complete = try columns[2].bool()
                    let created = formatter.date(from: try columns[3].string())!
                    
                    let todo = ToDo(
                        id: UUID(uuidString: id)!,
                        description: description,
                        complete: complete,
                        created: created)
                    
                    todos.append(todo)
                }
                
                return todos
            }
            
            DispatchQueue.main.async {
                completion(result)
            }
        }
    }
    
    func add(todo: ToDo, completion: @escaping (Result<Bool, Error>) -> Void) {
        let formatter = self.dateFormatter
        connectionPool.withConnection { connectionResult in
            
            let result = Result<Bool, Error> {
                
                let connection = try connectionResult.get()
                let sql = "INSERT INTO \(self.todosPath) VALUES ('\(todo.id.uuidString)', '\(todo.description)', '\(todo.complete)', '\(formatter.string(from: todo.created))');"
                let statment = try connection.prepareStatement(text: sql)
                defer { statment.close() }
                
                try statment.execute()
                
                return true
            }
            
            DispatchQueue.main.async {
                completion(result)
            }
        }
    }
    
    func update(todo: ToDo, completion: @escaping (Result<Bool, Error>) -> Void) {
        connectionPool.withConnection { connectionResult in
            
            let result = Result<Bool, Error> {
                
                let connection = try connectionResult.get()
                let sql = "UPDATE \(self.todosPath) SET (description, complete) =  ('\(todo.description)', '\(todo.complete)') WHERE id = '\(todo.id.uuidString)';"
                let statment = try connection.prepareStatement(text: sql)
                defer { statment.close() }
                
                try statment.execute()
                
                return true
            }
            
            DispatchQueue.main.async {
                completion(result)
            }
        }
    }
    
    func delete(todo: ToDo, completion: @escaping (Result<Bool, Error>) -> Void) {
        delete(todoId: todo.id, completion: completion)
    }

    func delete(todoId: UUID, completion: @escaping (Result<Bool, Error>) -> Void) {
        connectionPool.withConnection { connectionResult in
            
            let result = Result<Bool, Error> {
                
                let connection = try connectionResult.get()
                let sql = "DELETE FROM \(self.todosPath) WHERE id = '\(todoId.uuidString)';"
                let statment = try connection.prepareStatement(text: sql)
                defer { statment.close() }
                
                try statment.execute()
                
                return true
            }
            
            DispatchQueue.main.async {
                completion(result)
            }
        }
    }

    func delete(ids: [UUID], completion: @escaping (Result<Bool, Error>) -> Void) {
        
        var idListString = ids.reduce("") { initial, current in
            "\(initial),'\(current)'"
        }
        
        idListString = idListString.hasPrefix(",") ? String(idListString.dropFirst()) : idListString
        
        connectionPool.withConnection { connectionResult in
            
            let result = Result<Bool, Error> {
                
                let connection = try connectionResult.get()
                let sql = "DELETE FROM \(self.todosPath) WHERE id IN (\(idListString));"
                let statment = try connection.prepareStatement(text: sql)
                defer { statment.close() }
                
                try statment.execute()
                
                return true
            }
            
            DispatchQueue.main.async {
                completion(result)
            }
        }
    }
}
