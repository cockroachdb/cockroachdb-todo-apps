#/bin/bash

# Copyright 2020 Jack Elendil B. Lagare

#   Licensed under the Apache License, Version 2.0 (the "License");
#   you may not use this file except in compliance with the License.
#   You may obtain a copy of the License at

#       http://www.apache.org/licenses/LICENSE-2.0

#   Unless required by applicable law or agreed to in writing, software
#   distributed under the License is distributed on an "AS IS" BASIS,
#   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#   See the License for the specific language governing permissions and
#   limitations under the License.

create(){
    echo '== Create a new task =='
    read -p 'Description: ' description
    sqlStatement="INSERT INTO todo.tasks (description) VALUES ('$description');"
    docker exec -it roach1 ./cockroach sql --insecure --execute="$sqlStatement"
    
    echo ''
    echo ''
}

view(){
    echo '== View a task =='
    read -p 'Task ID: ' taskId
    sqlStatement="SELECT * FROM todo.tasks WHERE id = $taskId;"
    docker exec -it roach1 ./cockroach sql --insecure --execute="$sqlStatement"
    
    echo ''
    echo ''
}

while [ "$choice" != '5' ]
do
    echo 'Hello! This is a Todo App using Bash and CockroachDb!'
    echo 'To begin, please choose an action: '
    echo '[1] Create a new task'
    echo '[2] View task'
    echo '[3] Delete task'
    echo '[4] Show all tasks'
    echo '[5] Exit'
    echo
    echo 'Enter number of your choice:'
    read -p '> ' choice

    case "$choice" in
        "1")
            create
            ;;
        "2")
            view
            ;;
        "3")
            echo '3'
            ;;
        "4")
            echo '4'
            ;;
    esac
done


