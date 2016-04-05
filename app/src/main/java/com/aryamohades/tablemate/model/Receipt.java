package com.aryamohades.tablemate.model;

public class Receipt {
    private String receipt_id;
    private String customer_id;
    private Double total_bill;
    private String time;
    private Restaurant restaurant;

    public Double getTotal() {
        return total_bill;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public String getTime() {
        return time;
    }
}
