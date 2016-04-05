package com.aryamohades.tablemate.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.aryamohades.tablemate.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TableActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, TableFragment.newInstance(), "table")
                .commit();
        }
    }

    @OnClick(R.id.nav_action)
    public void navAction() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            Toast.makeText(TableActivity.this, "Opening menu", Toast.LENGTH_SHORT).show();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
