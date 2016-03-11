package com.aryamohades.tablemate.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.aryamohades.tablemate.R;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;

public class StartActivity extends Activity {
    @Bind(R.id.btn_sign_in) Button signInBtn;
    @Bind(R.id.btn_register) Button registerBtn;

    @OnClick(R.id.btn_sign_in)
    public void signInActivity() {
        Intent i = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.btn_register)
    public void registerActivity() {
        Intent i = new Intent(StartActivity.this, RegisterActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }
}
