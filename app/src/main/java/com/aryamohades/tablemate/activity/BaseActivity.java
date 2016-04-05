package com.aryamohades.tablemate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.aryamohades.tablemate.model.User;
import com.aryamohades.tablemate.utils.PreferencesManager;

public class BaseActivity extends AppCompatActivity {
    protected PreferencesManager prefs;
    protected User user;
    protected String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new PreferencesManager(this);
        user = prefs.getUser();

        if (user != null) {
            token = user.token();
        }
    }
}
