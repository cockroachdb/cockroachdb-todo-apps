// Copyright [2020] [Muhammad Adeel Hussain]


use postgres::{Client, NoTls};
use std::env;

fn main() {
    let mut client =
        Client::connect("postgresql://todoapp@localhost:26257/todolists", NoTls).unwrap();

    let args: Vec<String> = env::args().collect();
    if args.is_empty() {
        println!("Help \n Use addtask id and todo to add a task \n use taskdone id to remove a task \n use tasklist to display lists of taks");
    }
    let query = &args[1];

    if query == "addtask" {
        let id = &args[2];
        let task = &args[3];

        // Create the "tasks" table.
        client
            .execute(
                "CREATE TABLE IF NOT EXISTS tasks (id STRING PRIMARY KEY, todo STRING)",
                &[],
            )
            .unwrap();

        // Insert a new todo.
        client
            .execute(
                "INSERT INTO tasks (id, todo) VALUES ($1, $2)",
                &[&id, &task],
            )
            .unwrap();
    } else if query == "taskdone" {
        //Remove a todo
        let id = &args[2];
        client
            .execute("DELETE FROM tasks WHERE id = $1", &[&id])
            .unwrap();
    } else if query == "tasklist" {
        // Print out the tasks.
        println!("Task List:");
        for row in &client.query("SELECT id, todo FROM tasks", &[]).unwrap() {
            let id: String = row.get(0);
            let task: String = row.get(1);
            println!("{}:  {}", id, task);
        }
    } else {
        eprint!("Wrong Query");
    }
}
