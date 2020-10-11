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

update(){
    echo '== Update a task =='
    read -p 'Task ID: ' taskId
    read -p 'Field to Update (Description or Status): ' field
    read -p 'Value: ' value

    if [ "$field" = "Status" ]
    then
        if [ "$value" != "TO DO" ] && [ "$value" != "IN PROGRESS" ] && [ "$value" != "DONE" ]
        then
            echo "Please provide any of the valid values: TO DO | IN PROGRESS | DONE"
            return
        fi

        sqlStatement="UPDATE todo.tasks SET status = '$value' WHERE id = $taskId;"
        docker exec -it roach1 ./cockroach sql --insecure --execute="$sqlStatement"
        return
    
    fi

    if [ "$field" = "Description" ]
    then
        sqlStatement="UPDATE todo.tasks SET description = '$value' WHERE id = $taskId;"
        docker exec -it roach1 ./cockroach sql --insecure --execute="$sqlStatement"
        return
    fi
    
    echo ''
    echo ''
        
}

showAll(){
    echo '== All Tasks =='
    sqlStatement="SELECT * FROM todo.tasks;"
    docker exec -it roach1 ./cockroach sql --insecure --execute="$sqlStatement"
}

while [ "$choice" != '6' ]
do
    echo 'Hello! This is a Todo App using Bash and CockroachDb!'
    echo 'To begin, please choose an action: '
    echo '[1] Create a new task'
    echo '[2] View task'
    echo '[3] Update task'
    echo '[4] Delete task'
    echo '[5] Show all tasks'
    echo '[6] Exit'
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
            update
            ;;
        "5")
            showAll
            ;;
    esac
done


