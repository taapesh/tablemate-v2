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

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class BusinessLoginFragment extends BaseFragment {

    public static BusinessLoginFragment newInstance() {
        return new BusinessLoginFragment();
    }

    public BusinessLoginFragment() {
    }

    @OnClick(R.id.login)
    public void businessLogin() {
        ServiceFactory.createService(AuthService.class, AuthService.ENDPOINT)
                .login("testserver@gmail.com", "12345")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("BusinessLogin", e.getMessage());
                    }

                    @Override
                    public void onNext(User user) {
                        // TODO: check if server is waiting tables
                        final PreferencesManager prefs = new PreferencesManager(getActivity());
                        prefs.setServerLoggedIn(true);
                        prefs.setUser(user);
                        Intent i = new Intent(getActivity(), ServerHomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_business_login, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Business Sign In", R.drawable.back_arrow);
        return view;
    }
}
