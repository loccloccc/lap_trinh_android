package com.example.buoi4.model;

public class Product {
    private int id;
    private String name;
    private String material;
    private String origin;
    private double price;
    private int categoryId;

    public Product(int id, String name, String material, String origin, double price, int categoryId) {
        this.id = id;
        this.name = name;
        this.material = material;
        this.origin = origin;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Product(String name, String material, String origin, double price, int categoryId) {
        this.name = name;
        this.material = material;
        this.origin = origin;
        this.price = price;
        this.categoryId = categoryId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getMaterial() { return material; }
    public String getOrigin() { return origin; }
    public double getPrice() { return price; }
    public int getCategoryId() { return categoryId; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMaterial(String material) { this.material = material; }
    public void setOrigin(String origin) { this.origin = origin; }
    public void setPrice(double price) { this.price = price; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
}