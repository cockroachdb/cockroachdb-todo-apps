create database
```
$cockroach sql --certs-dir=certs --host=localhost:26257
>CREATE USER IF NOT EXISTS django;
>CREATE DATABASE todo;
>GRANT ALL ON DATABASE todo TO django;
```

start cluster

```
>cockroach start --certs-dir=certs --listen-addr=localhost:26257 --http-addr=localhost:8080 --join=localhost:26257,localhost:26258,localhost:26259
```
