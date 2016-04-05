package com.aryamohades.tablemate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.model.User;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.UserService;
import com.aryamohades.tablemate.utils.PreferencesManager;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User user = new PreferencesManager(this).getUser();

        if (user == null) {
            Intent i = new Intent(SplashActivity.this, StartActivity.class);
            startActivity(i);
        } else if (prefs.isServing()) {
            Intent i = new Intent(SplashActivity.this, ServerActivity.class);
            startActivity(i);
        } else if (prefs.isServerLoggedIn()) {
            Intent i = new Intent(SplashActivity.this, ServerHomeActivity.class);
            startActivity(i);
        } else {
            ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
                    .getTable("Token " + user.getAccessToken())
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Response<Table>>() {
                        @Override
                        public void onCompleted() {
                            // not implemented
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("SplashActivity", e.getMessage());
                        }

                        @Override
                        public void onNext(Response<Table> res) {
                            Table table = res.body();
                            if (table != null) {
                                prefs.setTable(table);
                                Intent i = new Intent(SplashActivity.this, TableActivity.class);
                                startActivity(i);
                            } else {
                                Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                                startActivity(i);
                            }
                        }
                    });
        }
    }
}
