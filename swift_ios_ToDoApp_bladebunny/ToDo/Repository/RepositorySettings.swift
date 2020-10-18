//
//  RepositorySettings.swift
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

struct RepositorySettings: Codable {
    
    let host: String
    let port: Int
    let ssl: Bool
    let database: String
    
    static var shared: RepositorySettings = {
        
        guard let url = Bundle.main.url(forResource: "RepositorySettings", withExtension: "json") else {
            fatalError("RepositorySettings.json not found")
        }
        
        let settings: RepositorySettings
        
        do {
            let data = try Data(contentsOf: url)
            settings = try JSONDecoder().decode(RepositorySettings.self, from: data)
        } catch {
            fatalError("Error reading RepositorySettings.json: \(error)")
        }
        
        Postgres.logger.info("RepositorySettings: \(settings)")
        return settings
    }()
}
