#!/bin/bash

'''
Add the following header in a comment at the beginning of each source
file, and fill in your name and the year.

   Copyright [2020] [prantoran]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
'''

if [ $1 == "install" ]; then

    docker network create -d bridge roachnet


    --name=roach1 \
    --hostname=roach1 \
    --net=roachnet \
    -p 26257:26257 -p 8080:8080  \
    -v "${PWD}/cockroach-data/roach1:/cockroach/cockroach-data"  \
    cockroachdb/cockroach:v20.1.8 start \
    --insecure \
    --join=roach1,roach2,roach3


    docker run -d \
    --name=roach2 \
    --hostname=roach2 \
    --net=roachnet \
    -v "${PWD}/cockroach-data/roach2:/cockroach/cockroach-data" \
    cockroachdb/cockroach:v20.1.8 start \
    --insecure \
    --join=roach1,roach2,roach3


    docker run -d \
    --name=roach3 \
    --hostname=roach3 \
    --net=roachnet \
    -v "${PWD}/cockroach-data/roach3:/cockroach/cockroach-data" \
    cockroachdb/cockroach:v20.1.8 start \
    --insecure \
    --join=roach1,roach2,roach3


    docker exec -it roach1 ./cockroach init --insecure
    grep 'node starting' cockroach-data/roach1/logs/cockroach.log -A 11

else
    docker start roach1, roach2, roach3
fi
