"""
Contributer's Name - Subramani Ramadas
(C) 2020 Subramani Ramadas

"""

# To Do Application
A simple dotnet todoapp

### Steps to setup locally
+ [Install CockroachDB](https://www.cockroachlabs.com/docs/v20.1/install-cockroachdb-mac.html)
+ [Start a local cluster](https://www.cockroachlabs.com/docs/v20.1/start-a-local-cluster)
+ Create user, database and grant permissions
+ [Install dotnet](https://dotnet.microsoft.com/download)
+ Run the application

# Create user, database and grant permissions
CREATE USER IF NOT EXISTS subramani;
CREATE DATABASE tododb;
GRANT ALL ON DATABASE tododb TO subramani;

# Run the application
dotnet run