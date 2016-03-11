package com.aryamohades.tablemate.model;

public class ServerRegistration {
    private String server_id;
    private String restaurant_name;
    private String restaurant_addr;
    private boolean active;

    public String getServerId() {
        return server_id;
    }

    public String getRestaurantName() {
        return restaurant_name;
    }

    public String getRestaurantAddr() {
        return restaurant_addr;
    }

    public boolean isActive() {
        return active;
    }
}
