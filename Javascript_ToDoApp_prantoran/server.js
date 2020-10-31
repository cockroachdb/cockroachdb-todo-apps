/**
 *    Copyright [2020] [prantoran]

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

const express = require('express');
const Sequelize = require('sequelize-cockroachdb');

let sequelize = new Sequelize('nodejstodo', 'admin', '', {
    dialect: 'postgres',
    port: 26257,
    logging: false
  });

let Todo = sequelize.define('todo', {
    id: { type: Sequelize.INTEGER, primaryKey: true, allowNull: false, autoIncrement: true },
    task: { type: Sequelize.STRING, unique: true },
    status: { type: Sequelize.INTEGER, defaultValue: 0 }
});

Todo.sync({force: true}).then(function() {
    return Todo.bulkCreate([
        {id: 1, task: "finish ch1", status: 1},
        {id: 2, task: "finish ch2", status: 0}
    ]);
}).then(function() {
    return Todo.findAll();
}).then(function(todos) {
    todos.forEach(function(todo) {
        console.log(todo.id + ' ' + todo.status + ' ' + todo.task);
    });
    // process.exit(0);
}).catch(function(err) {
    console.error('error: ' + err.message);
    process.exit(1);
})


const bodyParser = require("body-parser");

const app = express();
app.use(bodyParser.urlencoded({ extended: true }));

app.set('view engine', 'ejs');

app.get('/', (req, res) => {
    Todo.findAll().then(data => {
        res.render("index", {todos: data});
        // console.log(data);
        // res.send('Hello prantoran');
    }).catch(err => res.status(400).json(err));

});

app.post('/addTask', (req, res) => {
    const { textTodo } = req.body;
    Todo.create({
        task: textTodo
    }).then(
        _ => {
            res.redirect('/');
        }
    ).catch(err => {
        res.status(400).json({ message: 'unable to create a new task' });
    });
});


app.put('/moveTaskDone', (req, res) => {
    const { name, id } = req.body;
    if (name == 'todo') {
        Todo.find({ where: { id: id } })
            .then(function(todo) {
                return todo.update({
                    status: 1
                })
            }).then(function(task) {
                console.log(id, "updated");
                res.json(task[0]);
            }).catch(err => {
                console.log(err);
            })
    } else {
        Todo.find({ where: { id: id } })
        .then(function(todo) {
            return todo.update({
                status: 0
            })
        }).then(function(task) {
            console.log(id, "updated");
            res.json(task[0]);
        }).catch(err => {
            console.log(err);
        })
    }
});

app.listen(5000, () => {
    console.log("app is running on port 5000");
})