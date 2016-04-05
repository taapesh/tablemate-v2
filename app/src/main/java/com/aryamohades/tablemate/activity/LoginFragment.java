package com.aryamohades.tablemate.activity;

import android.content.Intent;
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
import com.aryamohades.tablemate.utils.PreferencesManager;
import com.aryamohades.tablemate.utils.TestAPI;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginFragment extends BaseFragment {
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
    }

    @OnClick(R.id.business_login)
    public void businessLogin() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, BusinessLoginFragment.newInstance(), "businessLogin")
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.login)
    public void login() {
        AuthService service = ServiceFactory.createService(AuthService.class, AuthService.ENDPOINT);
        service.login(TestAPI.CUSTOMER_EMAIL, TestAPI.PASSWORD)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        // Not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Login", e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        // TODO: check if user has table first, add table to response JSON
                        new PreferencesManager(getActivity()).setUser(user);
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Sign In", R.drawable.back_arrow);
        return view;
    }
}
