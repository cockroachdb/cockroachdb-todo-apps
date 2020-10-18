//
//  ToDoListView.swift
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

import SwiftUI

struct ToDoListView: View {
    
    @ObservedObject var viewModel: ToDoViewModel
    
    var body: some View {
        NavigationView {
            if viewModel.showError {
                VStack {
                    Image(systemName: "exclamationmark.shield")
                        .resizable()
                        .foregroundColor(.red)
                        .frame(width: 64, height: 64, alignment: .center)
                    Text(viewModel.currentError!.localizedDescription)
                        .multilineTextAlignment(.center)
                        .font(.title)
                        .padding(.vertical, 16)
                }
            } else {
                Group {
                    if viewModel.showEmpty {
                        VStack {
                            Image(systemName: "umbrella")
                                .resizable()
                                .frame(width: 64, height: 64, alignment: .center)
                            Text("No Todos")
                                .multilineTextAlignment(.center)
                                .font(.title)
                                .padding(.vertical, 16)
                        }
                    } else {
                        List {
                            create(section: .open)
                            create(section: .complete)
                        }
                        .listStyle(GroupedListStyle())
                        .animation(.default)
                    }
                }
                .navigationBarTitle("My ToDos")
                .navigationBarItems(trailing:
                                        NavigationLink(
                                            destination: ToDoView(todo: EditingToDo(
                                                                    description: "[New]",
                                                                    complete: false),
                                                                    update: viewModel.upsert),
                                            label: {
                                                Image(systemName: "plus")
                                            })
                )
            }
        }
        .onAppear {
            DispatchQueue.main.async {
                viewModel.loadToDos()
            }
        }
        .onDisappear {
            viewModel.disconnect()
        }
    }
    
    @ViewBuilder
    func create(section: ToDoViewModel.Section) -> some View {
        Section(header: Text(section == .open ? "Open" : "Complete")) {
            ForEach(section == .open ? viewModel.openTodos : viewModel.completeTodos) { todo in
                NavigationLink(
                    destination: ToDoView(todo: EditingToDo(id: todo.id,
                                                description: todo.description,
                                                complete: todo.complete),
                                          update: viewModel.upsert),
                    label: {
                        createTodoView(todo: todo)
                    })
            }
            .onDelete { indices in
                self.viewModel.delete(at: indices, section: section)
            }
        }
    }
    
    @ViewBuilder
    func createTodoView(todo: ToDo) -> some View {
        
        HStack {
            if todo.complete {
                Image(systemName: "checkmark.circle")
            } else {
                Image(systemName: "circle")
            }
            
            Text(todo.description)
        }
    }
}
