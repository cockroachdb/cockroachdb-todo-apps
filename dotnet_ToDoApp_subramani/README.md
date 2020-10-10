# To Do Application
A simple dotnet todoapp

### Steps to setup locally
+ [Setup CockroachDB (insecure/secure)](https://www.cockroachlabs.com/docs/v20.1/install-cockroachdb-mac.html)
+ [Install psycopg-binary(for dev only, psycopg for prod)](https://pypi.org/project/psycopg2-binary/)
+ Install requirements and create table

##### Install Requierements
`pip install -r requirements.txt`
<p>Start cockroach db cluster and create table</p>

```
cockroach start \
> --insecure \
> --store=node1 \
> --listen-addr=localhost:26257 \
> --http-addr=localhost:8080 \
> --join=localhost:26257,localhost:26258,localhost:26259 \
> --background

# init
cockroach init --insecure --host=localhost:26257

# create db user and permission
CREATE USER IF NOT EXISTS django;
CREATE DATABASE bank;
GRANT ALL ON DATABASE bank TO django;
```

##### Migrations and Run Server
```
python manage.py makemigrations
python manage.py createsuperuser
python manage.py runserver
```
All set now you can create category using django admin and start adding tasks to your todo app