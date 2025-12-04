# Go To-Do App with CockroachDB

A simple To-Do application built with Go and CockroachDB.

## Prerequisites

- [Go](https://golang.org/dl/) (1.16 or later)
- [CockroachDB](https://www.cockroachlabs.com/docs/stable/install-cockroachdb.html)

## Setup Instructions

### 1. Start CockroachDB

Start a local CockroachDB cluster in insecure mode:

```bash
cockroach start-single-node --insecure --listen-addr=localhost:26257 --http-addr=localhost:8081
```

### 2. Initialize the Database

Run the setup script to create the database schema:

```bash
chmod +x setup.sh
./setup.sh
```

**Note:** This uses `cockroach sql` command, NOT `psql`. CockroachDB has its own SQL client.

Alternatively, you can manually create the schema:

```bash
cockroach sql --insecure --host=localhost:26257 --database=defaultdb < db/schema.sql
```

### 3. Install Go Dependencies

```bash
go mod download
```

### 4. Run the Application

```bash
go run main.go
```

The server will start on `http://localhost:8080`.

## Environment Variables

The application uses the following environment variables (defined in `.env`):

- `DB_URL`: CockroachDB connection string (default: `postgresql://root@localhost:26257/defaultdb?sslmode=disable`)
- `PORT`: Server port (default: `8080`)

## API Endpoints

- `GET /` - Home page
- `GET /get-todos` - Get all todos
- `POST /create-todo` - Create a new todo
- `POST /delete-todo` - Delete a todo
- `POST /update-todo` - Update a todo

## Database Schema

The application uses a single `todos` table:

```sql
CREATE TABLE IF NOT EXISTS todos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    text STRING,
    checked BOOL
);
```

## Important Notes

- **Use CockroachDB SQL client**: This app is designed for CockroachDB. Use `cockroach sql` instead of `psql`.
- **Connection String**: The connection string uses the PostgreSQL protocol (`postgresql://`) which is compatible with CockroachDB.
- **Driver**: The app uses `github.com/lib/pq` driver, which works with CockroachDB.
