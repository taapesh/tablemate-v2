package com.aryamohades.tablemate.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.aryamohades.tablemate.model.User;
import com.google.gson.Gson;

public class PreferencesManager {
    private static final String MY_PREFERENCES = "preferences";
    private static final String USER = "user";
    private static final String AUTH_TOKEN  = "auth_token";
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

    public boolean clear() {
        return prefs.edit().clear().commit();
    }
}
