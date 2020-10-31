package com.cockroachlabs.todoapps;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        ToDoDAO dao = new ToDoDAO();

        TodoItem learnJava = new TodoItem("Learn Java");
        TodoItem learnCockroachDB = new TodoItem("Learn CockroachDB");

        System.out.println("======== CockroachDB TodoApps with Pure Java ======== ");

        System.out.println("== CLEAN TABLE ==");
        dao.removeAll();

        System.out.println("== INSERT ==");

        dao.newItem(learnJava);
        dao.newItem(learnCockroachDB);

        System.out.println("== LIST ==");

        List<TodoItem> list = dao.list();

        list.forEach(System.out::println);

        System.out.println("== REMOVE ==");

        dao.removeItem(learnJava);

        System.out.println("==  NEW LIST ==");
        List<TodoItem> newList = dao.list();
        newList.forEach(System.out::println);

    }
}
