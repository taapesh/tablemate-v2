package com.aryamohades.tablemate.model;

public class User {
    private String user_id;
    private String first_name;
    private String last_name;
    private String email;
    private String auth_token;

    public String token() {
        return "Token " + auth_token;
    }

    public String getAccessToken() {
        return auth_token;
    }

    public String getUserId() {
        return user_id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getFullName() {
        return first_name + " " + last_name;
    }
}
