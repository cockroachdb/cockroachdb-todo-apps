## TODO app with Django example using CockroackDB :snake: 

Hi, here is a simple Backend service where you can get, create, update and delete items (simple CRUD operetions).

I use Docker to start DB cluster and followed the below documentation. 
DB only have one table with the items created by user

### Endpoints & examples
| Routs | Request | Response | Method | Status |
| --- | --- | --- | --- | --- |
| item/ | . | [{ "id": "123456789", "description": "create todo app"}, { "id": "987654321", "description": "learn cockroachDB"}] | GET | 200 |
| item/(id) | { "description": "learn Django" } | . | POST | 201 |
| item/(id) | { "description": "learn Django" } | . | PUT | 200/400 |
| item/(id) | . | . | DELETE | 200/404 |

I want to learn more about cockroach and django so I want to continue contributing on this.

### Official Docs & Links
[CockroachDB Docs](https://www.cockroachlabs.com/docs/stable/)
[How to build python app step by step from cockroachDB](https://www.cockroachlabs.com/docs/stable/build-a-python-app-with-cockroachdb-django.html)
[Start a Cluster in Docker](https://www.cockroachlabs.com/docs/v20.1/start-a-local-cluster-in-docker-mac.html)

