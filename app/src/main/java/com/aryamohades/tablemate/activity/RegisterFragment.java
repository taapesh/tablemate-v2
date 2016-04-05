package com.aryamohades.tablemate.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.User;
import com.aryamohades.tablemate.service.AuthService;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.utils.TestAPI;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterFragment extends BaseFragment {
    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    public RegisterFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Register", R.drawable.back_arrow);
        return view;
    }

    @OnClick(R.id.register)
    public void register() {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.ENDPOINT);

        service.register(TestAPI.FIRST_NAME, TestAPI.LAST_NAME, TestAPI.CUSTOMER_EMAIL, TestAPI.PASSWORD)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<User>() {
                @Override
                public void onCompleted() {
                    // not implemented
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Register", e.getMessage());
                }

                @Override
                public void onNext(User user) {
                    // Todo: set user and go to customer home activity
                }
            });
    }
}
