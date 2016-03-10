package com.aryamohades.tablemate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.User;
import com.aryamohades.tablemate.service.AuthService;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.utils.PreferencesManager;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseUserActivity extends AppCompatActivity {
    private final String EMAIL = "test@gmail.com";
    private final String PASSWORD = "12345";
    private PreferencesManager prefs;

    @Bind(R.id.chooseCustomerBtn) Button customerBtn;
    @Bind(R.id.chooseServerBtn) Button serverBtn;

    @OnClick(R.id.chooseCustomerBtn)
    public void customerHome() {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.API_BASE);
        service.login(EMAIL, PASSWORD)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Choose User Activity", e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        prefs.setUser(user);
                        Intent i = new Intent(ChooseUserActivity.this, CustomerHomeActivity.class);
                        startActivity(i);
                    }
                });

    }

    @OnClick(R.id.chooseServerBtn)
    public void serverHome() {
        // Login with server
        Intent i = new Intent(ChooseUserActivity.this, ServerHomeActivity.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user);
        ButterKnife.bind(this);
        prefs = new PreferencesManager(this);
    }
}
