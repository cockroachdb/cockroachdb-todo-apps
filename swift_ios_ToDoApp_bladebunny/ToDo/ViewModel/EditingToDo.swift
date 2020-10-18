//
//  EditingToDo.swift
//  ToDo
//
//  Created by Tim Brooks on 10/18/20.
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

class EditingToDo: ObservableObject {
    
    @Published var description: String
    @Published var complete: Bool
    let id: UUID?
    let created: Date
    let initialDescription: String
    let initialComplete: Bool
    var wasMutated: Bool {
        return (self.initialComplete != self.complete) || (self.initialDescription != self.description)
    }

    init(id: UUID? = nil, description: String, complete: Bool, created: Date = Date()) {
        self.id = id
        self.initialDescription = description
        self.initialComplete = complete
        self.description = description
        self.complete = complete
        self.created = created
    }
}
