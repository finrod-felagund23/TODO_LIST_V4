package com.tirkisovkadyr.todolistv4;

public class Todo {
    private int id;
    private String theme;
    private String description;

    public Todo() {

    }

    public Todo(int id, String theme, String description) {
        this.id = id;
        this.theme = theme;
        this.description = description;
    }

    public int getId() { return this.id; }
    public String getTheme() { return this.theme; }
    public String getDescription() { return this.description; }

    public void setId(int id) { this.id = id; }
    public void setTheme(String theme) { this.theme = theme; }
    public void setDescription(String description) { this.description = description; }

}
