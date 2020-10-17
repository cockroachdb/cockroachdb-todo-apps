create database

$cockroach sql --certs-dir=certs --host=localhost:26257
>CREATE USER IF NOT EXISTS django;
>CREATE DATABASE todo;
>GRANT ALL ON DATABASE todo TO django;

start cluster

C:\Users\Abhilasha\cockroach-v20.1.6.windows-6.2-amd64>cockroach start --certs-dir=certs --listen-addr=localhost:26257 --http-addr=localhost:8080 --join=localhost:26257,localhost:26258,localhost:26259