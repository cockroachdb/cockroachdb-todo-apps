# iOS / SwiftUI ToDo App Example

## Description
Demonstrates using CockroachDB from an iOS client using Swift and SwiftUI

## Setup
- [Install CockroachDB](https://www.cockroachlabs.com/docs/v20.1/install-cockroachdb-mac.html)
- [Start a CockroachDB cluster](https://www.cockroachlabs.com/docs/v20.1/start-a-local-cluster)
- Update *RepositorySettings.json* in the project with your configuration
- Resolve Swift packages
- Build and run

## Features
- App demonstrates full CRUD operations (*Repository.swift*)
- App will create the main table (ToDos) if needed on start-up; only necessary to set up cluster
- App demonstrates aspects of SwiftUI and Combine

## ToDo Table Definition
```
CREATE TABLE defaultdb.todos (id STRING PRIMARY KEY, description STRING NOT NULL, complete BOOL NOT NULL, created STRING NOT NULL);
```

## Author
Tim Brooks ([@bladebunny](https://github.com/bladebunny))

## Thanks
- David Pitfield ([@pitfield](https://github.com/pitfield)) for [PostgresClientKit](https://github.com/codewinsdotcom/PostgresClientKit)

## License
[Apache, Version 2.0](    http://www.apache.org/licenses/LICENSE-2.0)
