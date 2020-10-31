package com.cockroachlabs.todoapps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class BasicDAO {

    private static final int    MAX_RETRY_COUNT = 3;
    private static final String SAVEPOINT_NAME  = "cockroach_restart";
    private static final String RETRY_SQL_STATE = "40001";

    private final DataSource ds;

    BasicDAO(DataSource ds) {
        this.ds = ds;
    }

    /**
     * Adapted from
     * https://www.cockroachlabs.com/docs/stable/build-a-java-app-with-cockroachdb.html
     * 
     * Run SQL code in a way that automatically handles the
     * transaction retry logic so we don't have to duplicate it in
     * various places.
     *
     * @param sqlCode
     *            a String containing the SQL code you want to
     *            execute. Can have placeholders, e.g., "INSERT INTO accounts
     *            (id, balance) VALUES (?, ?)".
     *
     * @param args
     *            String Varargs to fill in the SQL code's
     *            placeholders.
     * @return Integer Number of rows updated, or -1 if an error is thrown.
     */
    public Integer runSQL(String sqlCode, String... args) {

        // This block is only used to emit class and method names in
        // the program output. It is not necessary in production
        // code.
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement elem = stacktrace[2];
        String callerClass = elem.getClassName();
        String callerMethod = elem.getMethodName();

        int rv = 0;

        try (Connection connection = ds.getConnection()) {

            // We're managing the commit lifecycle ourselves so we can
            // automatically issue transaction retries.
            connection.setAutoCommit(false);

            int retryCount = 0;

            while (retryCount < MAX_RETRY_COUNT) {

                Savepoint sp = connection.setSavepoint(SAVEPOINT_NAME);

                try (PreparedStatement pstmt = connection.prepareStatement(sqlCode)) {

                    // Loop over the args and insert them into the
                    // prepared statement based on their types. In
                    // this simple example we classify the argument
                    // types as "integers" and "everything else"
                    // (a.k.a. strings).
                    for (int i = 0; i < args.length; i++) {
                        int place = i + 1;
                        String arg = args[i];

                        try {
                            int val = Integer.parseInt(arg);
                            pstmt.setInt(place, val);
                        } catch (NumberFormatException e) {
                            pstmt.setString(place, arg);
                        }
                    }

                    if (pstmt.execute()) {
                        // We know that `pstmt.getResultSet()` will
                        // not return `null` if `pstmt.execute()` was
                        // true
                        ResultSet rs = pstmt.getResultSet();
                        ResultSetMetaData rsmeta = rs.getMetaData();
                        int colCount = rsmeta.getColumnCount();

                        // This printed output is for debugging and/or demonstration
                        // purposes only. It would not be necessary in production code.
                        System.out.printf("\n%s.%s:\n    '%s'\n", callerClass, callerMethod, pstmt);

                        while (rs.next()) {
                            for (int i = 1; i <= colCount; i++) {
                                String name = rsmeta.getColumnName(i);
                                String type = rsmeta.getColumnTypeName(i);

                                // In this "bank account" example we know we are only handling
                                // integer values (technically 64-bit INT8s, the CockroachDB
                                // default). This code could be made into a switch statement
                                // to handle the various SQL types needed by the application.
                                if (type == "int8") {
                                    int val = rs.getInt(name);

                                    // This printed output is for debugging and/or demonstration
                                    // purposes only. It would not be necessary in production code.
                                    System.out.printf("    %-8s => %10s\n", name, val);
                                }
                            }
                        }
                    } else {
                        int updateCount = pstmt.getUpdateCount();
                        rv += updateCount;

                        // This printed output is for debugging and/or demonstration
                        // purposes only. It would not be necessary in production code.
                        System.out.printf("\n%s.%s:\n    '%s'\n", callerClass, callerMethod, pstmt);
                    }

                    connection.releaseSavepoint(sp);
                    connection.commit();
                    break;

                } catch (SQLException e) {

                    if (RETRY_SQL_STATE.equals(e.getSQLState())) {
                        System.out.printf(
                                "retryable exception occurred:\n    sql state = [%s]\n    message = [%s]\n    retry counter = %s\n",
                                e.getSQLState(), e.getMessage(), retryCount);
                        connection.rollback(sp);
                        retryCount++;
                        rv = -1;
                    } else {
                        rv = -1;
                        throw e;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.printf("BasicDAO.runSQL ERROR: { state => %s, cause => %s, message => %s }\n", e.getSQLState(),
                    e.getCause(), e.getMessage());
            rv = -1;
        }

        return rv;
    }

    public List<TodoItem> getList() {

        List<TodoItem> list = new ArrayList<>();
        try (Connection connection = ds.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM TODO");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TodoItem item = new TodoItem(rs.getString("description"));
                list.add(item);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.printf("BasicDAO.getList ERROR: { state => %s, cause => %s, message => %s }\n", e.getSQLState(),
                    e.getCause(), e.getMessage());
        }
        return list;
    }

}
