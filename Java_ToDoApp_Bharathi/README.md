#TodoApp

###Prerequisites
    1. Maven
    2. JDK 8+
    3. PostgreSQL DB

###Setup
    1. Update the DB connection URL, user name and password in application.properties 
    2. Update the server.port value in application.properties to your desired port
    3. Import the todo-request_collection.json in POSTman to view the request structure  
###Running the application    
    # Build the application
       mvn clean install
    
    # Start the application
        mvn spring-boot:run
   

###Endpoints
    /todo
    1. POST /create
    2. PUT /update/{id}
    3. DELETE /delete/{id}
    4. GET /getAll
    5. GET /get/{id}
    
    
###### Created by Bharathi BMS (C) 2020
