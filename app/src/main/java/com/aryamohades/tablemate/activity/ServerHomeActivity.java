package com.aryamohades.tablemate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.aryamohades.tablemate.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServerHomeActivity extends AppCompatActivity {
    @Bind(R.id.startServingBtn) Button startServingBtn;

    @OnClick(R.id.startServingBtn)
    public void startServing() {
        Intent i = new Intent(ServerHomeActivity.this, ServerActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_home);
        ButterKnife.bind(this);
    }
}
