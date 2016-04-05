package com.aryamohades.tablemate.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.aryamohades.tablemate.model.Restaurant;
import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.model.User;
import com.google.gson.Gson;

public class PreferencesManager {
    private static final String MY_PREFERENCES = "preferences";
    private static final String USER = "user";
    private static final String AUTH_TOKEN  = "auth_token";
    private static final String TABLE = "table";
    private static final String RESTAURANT = "restaurant";
    private static final String SERVER_LOGGED_IN = "server_logged_in";
    private static final String IS_SERVING = "is_serving";
    private static final String RESTAURANT_ID = "restaurant_id";
    private SharedPreferences prefs;

    public PreferencesManager(Context context) {
        prefs = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setUser(User user) {
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(USER, new Gson().toJson(user));
        prefsEditor.putString(AUTH_TOKEN, user.getAccessToken());
        prefsEditor.commit();
    }

    public User getUser() {
        String userJson = prefs.getString(USER, null);
        if (userJson != null) {
            return new Gson().fromJson(userJson, User.class);
        }
        return null;
    }

    public void setTable(Table table) {
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(TABLE, new Gson().toJson(table));
        prefsEditor.commit();
    }

    public Table getTable() {
        String tableJson = prefs.getString(TABLE, null);
        if (tableJson != null) {
            return new Gson().fromJson(tableJson, Table.class);
        }
        return null;
    }

    public void setRestaurant(Restaurant restaurant) {
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(RESTAURANT, new Gson().toJson(restaurant));
        prefsEditor.commit();
    }

    public Restaurant getRestaurant() {
        String restaurantJson = prefs.getString(RESTAURANT, null);
        if (restaurantJson != null) {
            return new Gson().fromJson(restaurantJson, Restaurant.class);
        }
        return null;
    }

    public void clearUser() {
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(USER, null);
        prefsEditor.commit();
    }

    public boolean clear() {
        return prefs.edit().clear().commit();
    }

    public void setIsServing(boolean isServing) {
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(IS_SERVING, isServing);
        prefsEditor.commit();
    }

    public boolean isServing() {
        return prefs.getBoolean(SERVER_LOGGED_IN, false);
    }

    public void setServerLoggedIn(boolean loggedIn) {
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(SERVER_LOGGED_IN, loggedIn);
        prefsEditor.commit();
    }

    public void setRestaurantId(String rId) {
        final SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(RESTAURANT_ID, rId);
        prefsEditor.commit();
    }

    public String getRestaurantId() {
        return prefs.getString(RESTAURANT_ID, null);
    }

    public boolean isServerLoggedIn() {
        return prefs.getBoolean(SERVER_LOGGED_IN, false);
    }
}
