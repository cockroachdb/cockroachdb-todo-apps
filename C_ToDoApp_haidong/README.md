"""
Contributer's Name - Haidong Ji
(C) 2024 Haidong Ji 

"""

This sample app written in C is developed on Ubuntu 22.04 LTS. It should work on other Linux distros with adjustment such as package install.

It also uses a local Cockroach cluster. Please ensure it is running: `cockroach demo`.

### Step 1. PostgreSQL library

Ensure that you have CockroachDB's PostgreSQL wire compatibility library installed. You can do this by running:

```sh
sudo apt-get update
sudo apt-get install libpq-dev
```

### Step 2. Adjust password

Change in the source file to your password.

### Step 3. Build and run the program

Here is sample output:
```
~/git/hub/cockroachdb-todo-apps/build/Debug (C_ToDoApp_haidong ✗) ./outDebug 
Connected successfully!
Account 1 balance: 500
Account 2 balance: 200
Account 1 balance: 450
Account 2 balance: 250
```