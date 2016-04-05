package com.aryamohades.tablemate.model;

public class Server {
    private String server_id;
    private Restaurant restaurant;
    private String first_name;
    private String last_name;
    private String email;
    private boolean active;

    public String getServerId() {
        return server_id;
    }

    public boolean isActive() {
        return active;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }
}
