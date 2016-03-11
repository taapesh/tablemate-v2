package com.aryamohades.tablemate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.ServerRegistration;
import com.aryamohades.tablemate.model.User;
import com.aryamohades.tablemate.service.AuthService;
import com.aryamohades.tablemate.service.ServiceFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    private final String TEST_SERVER_EMAIL = "testserver@gmail.com";
    private final String TEST_CUSTOMER_EMAIL = "testcustomer@gmail.com";
    private final String TEST_PASS = "12345";
    private final String TEST_RESTAURANT_NAME = "Awesome Restaurant";
    private final String TEST_RESTAURANT_ADDR = "12345 Restaurant St.";


    @Bind(R.id.btn_register_customer_test) Button customerTestBtn;
    @Bind(R.id.btn_register_server_test) Button serverTestBtn;

    @OnClick(R.id.btn_register_customer_test)
    public void registerCustomer() {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.API_BASE);
        service.register("Bruce", "Wayne", TEST_CUSTOMER_EMAIL, TEST_PASS)
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
                        Toast.makeText(RegisterActivity.this, "Registered Customer", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @OnClick(R.id.btn_register_server_test)
    public void registerServer() {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.API_BASE);
        service.register("Alfred", "Pennyworth", TEST_SERVER_EMAIL, TEST_PASS)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        // Not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Register Activity", e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        registerEmployee(user.getUserId());
                    }
                });
    }

    private void registerEmployee(String id) {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.API_BASE);
        service.registerServer(id, TEST_RESTAURANT_NAME, TEST_RESTAURANT_ADDR)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ServerRegistration>() {
                    @Override
                    public void onCompleted() {
                        // Not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Register Activity", e.getMessage());
                    }

                    @Override
                    public void onNext(ServerRegistration serverRegistration) {
                        Toast.makeText(RegisterActivity.this, "Registered Server", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }
}
