//
//  ToDoView.swift
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

import SwiftUI

struct ToDoView: View {
    @Environment(\.presentationMode) private var presentationMode
    @ObservedObject var todo: EditingToDo
    private let update: (EditingToDo) -> Void
    
    init(todo: EditingToDo, update: @escaping (EditingToDo) -> Void = {_ in}) {
        self.todo = todo
        self.update = update
    }
    
    var body: some View {
        VStack {
            Form {
                TextField("Description", text: $todo.description)
                Toggle("Complete", isOn: $todo.complete)
            }
            Button((todo.id != nil) ? "Save" : "Add", action: {
                update(todo)
                presentationMode.wrappedValue.dismiss()
            })
            .disabled(!todo.wasMutated || todo.description.count == 0)
        }
        .navigationBarTitle((todo.id != nil) ? "Edit ToDo" : "New ToDo")
    }
}

struct ToDoView_Previews: PreviewProvider {
    static var previews: some View {
        ToDoView(todo: EditingToDo(description: "New", complete: false))
    }
}
