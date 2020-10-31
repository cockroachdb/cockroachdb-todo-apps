package com.cockroachlabs.todoapps;

import java.util.List;

import org.postgresql.ds.PGSimpleDataSource;

public class ToDoDAO {

    private BasicDAO dao;

    ToDoDAO() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(26257);
        ds.setDatabaseName("todo");
        ds.setUser("maxroach");
        ds.setPassword(null);
        ds.setSsl(false);
        ds.setReWriteBatchedInserts(true);
        ds.setApplicationName("TodoApps");
        dao = new BasicDAO(ds);
    }

    public void newItem(TodoItem item) {

        String description = item.getDescription();
        String[] args = { description };

        dao.runSQL("INSERT INTO todo(description) VALUES (?)", args);

    }

    public List<TodoItem> list() {
        return dao.getList();
    }

    public void removeItem(TodoItem item) {
        String description = item.getDescription();
        String[] args = { description };

        dao.runSQL("DELETE FROM todo WHERE description=?", args);
    }

    public void removeAll() {
        dao.runSQL("DELETE FROM todo");
    }
}
