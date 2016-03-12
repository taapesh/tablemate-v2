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
import com.aryamohades.tablemate.utils.TestAPI;

import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {

    @Bind(R.id.btn_register_customer_test) Button customerTestBtn;
    @Bind(R.id.btn_register_server_test) Button serverTestBtn;
    @Bind(R.id.btn_clear_tests) Button clearTestsBtn;

    @OnClick(R.id.btn_clear_tests)
    public void clearTests() {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.API_BASE);
        service.clearTests()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<JSONObject>>() {
                    @Override
                    public void onCompleted() {
                        // Not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RegisterActivity", e.getMessage());
                    }

                    @Override
                    public void onNext(Response<JSONObject> res) {
                        Log.d("RegisterActivity", "Cleared tests");
                    }
                });
    }

    @OnClick(R.id.btn_register_customer_test)
    public void registerCustomer() {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.API_BASE);

        Observable<User> o =
            service.register(
                    TestAPI.FIRST_NAME,
                    TestAPI.LAST_NAME,
                    TestAPI.CUSTOMER_EMAIL,
                    TestAPI.PASSWORD);

        o.subscribeOn(Schedulers.newThread())
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
        Observable<User> o =
            service.register(
                    TestAPI.SERVER_FIRST_NAME,
                    TestAPI.SERVER_LAST_NAME,
                    TestAPI.SERVER_EMAIL,
                    TestAPI.PASSWORD);

        o.subscribeOn(Schedulers.newThread())
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
                    Toast.makeText(RegisterActivity.this, "Registered Server", Toast.LENGTH_SHORT).show();
                    registerEmployee(user.getUserId());
                }
            });
    }

    private void registerEmployee(String id) {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.API_BASE);
        Observable<ServerRegistration> o =
            service.registerServer(
                id,
                TestAPI.RESTAURANT_NAME,
                TestAPI.RESTAURANT_ADDR);

        o.subscribeOn(Schedulers.newThread())
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
                    Toast.makeText(RegisterActivity.this, "Registered Employee", Toast.LENGTH_LONG).show();
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
