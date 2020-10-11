## Introduction
This is a simple TODO application implemented in Bash. It works by utilizing Docker commands to interface with the CockcroachDB client.

## Preparations

### CockroachDB
Note: These commands are based on what is written at https://www.cockroachlabs.com/docs/v20.1/start-a-local-cluster-in-docker-mac.html.

Execute the command below to create a network for Docker:

`docker network create -d bridge roachnet`

Start the cluster:

`docker run -d \
--name=roach1 \
--hostname=roach1 \
--net=roachnet \
-p 26257:26257 -p 8080:8080  \
-v "${PWD}/cockroach-data/roach1:/cockroach/cockroach-data"  \
cockroachdb/cockroach:v20.1.6 start \
--insecure \
--join=roach1,roach2,roach3
`

Initialize the cluster:

`
docker exec -it roach1 ./cockroach init --insecure`


## Running the Application

To start the application,

`sh run.sh`

To exit, just press `Ctrl+C` on your keyboard.