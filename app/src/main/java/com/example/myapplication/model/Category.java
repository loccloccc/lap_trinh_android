package com.example.myapplication.model;

public class Category {

    private int id;
    private String name;

    // Constructor đầy đủ
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor không id (dùng khi insert)
    public Category(String name) {
        this.name = name;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Hiển thị trong Spinner/List
    @Override
    public String toString() {
        return name;
    }
}