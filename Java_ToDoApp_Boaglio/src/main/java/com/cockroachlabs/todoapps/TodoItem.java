package com.cockroachlabs.todoapps;

public class TodoItem {

    private String description;

    public TodoItem(String description) {
        super();
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "TodoItem [ description=" + description + " ]";
    }

}
