package com.aryamohades.tablemate.model;

public class Order {
    private String order_id;
    private String customer_id;
    private boolean active;
    private boolean pending;
    private MenuItem item;
    private User customer;

    public MenuItem getItem() {
        return item;
    }

    public boolean isPending() {
        return pending;
    }

    public boolean isActive() {
        return active;
    }
}
