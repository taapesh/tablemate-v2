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
import com.aryamohades.tablemate.utils.TestAPI;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    private PreferencesManager prefs;

    @Bind(R.id.btn_sign_in) Button signInBtn;
    @Bind(R.id.btn_business_sign_in) Button businessSignInBtn;

    @OnClick(R.id.btn_sign_in)
    public void login() {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.API_BASE);
        Observable<User> o =
            service.login(TestAPI.CUSTOMER_EMAIL, TestAPI.PASSWORD);

        o.subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<User>() {
                @Override
                public void onCompleted() {
                    // Not implemented
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("LoginActivity", e.getMessage());
                }

                @Override
                public void onNext(User user) {
                    prefs.setUser(user);
                    Intent i = new Intent(LoginActivity.this, CustomerHomeActivity.class);
                    startActivity(i);
                }
            });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        prefs = new PreferencesManager(this);
    }
}
