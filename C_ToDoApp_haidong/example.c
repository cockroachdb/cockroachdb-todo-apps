#include <libpq-fe.h>
#include <stdio.h>
#include <stdlib.h>

void create_accounts(PGconn *conn) {
  PGresult *res = PQexec(
      conn,
      "CREATE TABLE IF NOT EXISTS accounts (id INT PRIMARY KEY, balance INT)");
  if (PQresultStatus(res) != PGRES_COMMAND_OK) {
    printf("SQL error: %s", PQerrorMessage(conn));
    PQclear(res);
    exit(1);
  }
  PQclear(res);

  res = PQexec(conn,
               "INSERT INTO accounts (id, balance) VALUES (1, 500), (2, 200)");
  if (PQresultStatus(res) != PGRES_COMMAND_OK) {
    printf("SQL error: %s", PQerrorMessage(conn));
    PQclear(res);
    exit(1);
  }
  PQclear(res);
}

void delete_accounts(PGconn *conn) {
  PGresult *res = PQexec(conn, "DROP TABLE IF EXISTS accounts");
  if (PQresultStatus(res) != PGRES_COMMAND_OK) {
    printf("SQL error: %s", PQerrorMessage(conn));
    PQclear(res);
    exit(1);
  }
  PQclear(res);
}

void transfer_funds(PGconn *conn, int from_account, int to_account,
                    int amount) {
  char query[100]; // Buffer for constructing the SQL queries

  // Begin transaction
  PGresult *res = PQexec(conn, "BEGIN");
  if (PQresultStatus(res) != PGRES_COMMAND_OK) {
    printf("Transaction start failed: %s", PQerrorMessage(conn));
    PQclear(res);
    return;
  }
  PQclear(res);

  // Update the balance of 'from' account
  sprintf(query, "UPDATE accounts SET balance = balance - %d WHERE id = %d",
          amount, from_account);
  res = PQexec(conn, query);
  if (PQresultStatus(res) != PGRES_COMMAND_OK) {
    printf("Update failed: %s", PQerrorMessage(conn));
    PQclear(res);
    sprintf(query, "ROLLBACK"); // Rollback the transaction on error
    PQexec(conn, query);
    PQclear(res);
    return;
  }
  PQclear(res);

  // Update the balance of 'to' account
  sprintf(query, "UPDATE accounts SET balance = balance + %d WHERE id = %d",
          amount, to_account);
  res = PQexec(conn, query);
  if (PQresultStatus(res) != PGRES_COMMAND_OK) {
    printf("Update failed: %s", PQerrorMessage(conn));
    PQclear(res);
    sprintf(query, "ROLLBACK"); // Rollback the transaction on error
    PQexec(conn, query);
    PQclear(res);
    return;
  }
  PQclear(res);

  // Commit the transaction if both updates are successful
  res = PQexec(conn, "COMMIT");
  if (PQresultStatus(res) != PGRES_COMMAND_OK) {
    printf("Transaction commit failed: %s", PQerrorMessage(conn));
    PQclear(res);
    sprintf(query, "ROLLBACK"); // Rollback in case of failure
    PQexec(conn, query);
    PQclear(res);
    return;
  }
  PQclear(res);
}

void print_balances(PGconn *conn) {
  PGresult *res = PQexec(conn, "SELECT id, balance FROM accounts");
  if (PQresultStatus(res) != PGRES_TUPLES_OK) {
    printf("SQL error: %s", PQerrorMessage(conn));
    PQclear(res);
    exit(1);
  }
  for (int i = 0; i < PQntuples(res); i++) {
    printf("Account %d balance: %s\n", atoi(PQgetvalue(res, i, 0)),
           PQgetvalue(res, i, 1));
  }
  PQclear(res);
}

int main(int argc, char *argv[]) {
  const char *host = "localhost";     // change to your host
  const char *port = "26257";         // change to your port
  const char *user = "demo";          // change to your username
  const char *dbname = "movr";        // change to your database name
  const char *password = "demo47567"; // change to your instance password

  // create connection parameters as a string
  char conn_str[300];
  sprintf(conn_str, "host=%s port=%s user=%s password=%s dbname=%s", host, port,
          user, password, dbname);

  // connect to the database
  PGconn *conn = PQconnectdb(conn_str);

  if (PQstatus(conn) != CONNECTION_OK) {
    printf("Unable to connect to the database: %s", PQerrorMessage(conn));
    PQfinish(conn);
    exit(1);
  }

  printf("Connected successfully!\n");

  // perform operations
  create_accounts(conn);

  print_balances(conn);

  transfer_funds(conn, 1, 2, 50);

  print_balances(conn);

  delete_accounts(conn);

  // clean up
  PQfinish(conn);

  return 0;
}
