#!/bin/bash

# Setup script for CockroachDB To-Do App

echo "Setting up CockroachDB To-Do App..."

# Check if CockroachDB is installed
if ! command -v cockroach &> /dev/null
then
    echo "Error: CockroachDB is not installed."
    echo "Please install it from: https://www.cockroachlabs.com/docs/stable/install-cockroachdb.html"
    exit 1
fi

# Create the database schema
echo "Creating database schema..."
cockroach sql --insecure --host=localhost:26257 --database=defaultdb < db/schema.sql

if [ $? -eq 0 ]; then
    echo "✓ Database schema created successfully!"
else
    echo "✗ Failed to create database schema"
    exit 1
fi

echo ""
echo "Setup complete! You can now run the application with:"
echo "  go run main.go"
