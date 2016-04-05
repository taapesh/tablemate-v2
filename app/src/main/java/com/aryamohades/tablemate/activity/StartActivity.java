package com.aryamohades.tablemate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.aryamohades.tablemate.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {
    private Animation anim;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        anim = AnimationUtils.loadAnimation(this, R.anim.slide_out_up);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, StartFragment.newInstance(), "start")
                    .commit();
        }
    }

    @OnClick(R.id.nav_action)
    public void navAction() {
        toolbar.startAnimation(anim);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            toolbar.startAnimation(anim);
        }
        super.onBackPressed();
    }
}
