package com.aryamohades.tablemate.model;

public class MenuItem {
    private String item_id;
    private String name;
    private String category;
    private Double price;
    private String description;
    private Restaurant restaurant;

    public String getId() {
        return item_id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }
}
