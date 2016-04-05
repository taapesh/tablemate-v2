package com.aryamohades.tablemate.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.aryamohades.tablemate.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartFragment extends Fragment {
    private Animation anim;
    private Toolbar toolbar;

    @OnClick(R.id.login)
    public void login() {
        toolbar.setVisibility(View.VISIBLE);
        toolbar.startAnimation(anim);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, LoginFragment.newInstance(), "login")
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.register)
    public void register() {
        toolbar.setVisibility(View.VISIBLE);
        toolbar.startAnimation(anim);

        getActivity().getSupportFragmentManager().beginTransaction()
            .replace(R.id.content, RegisterFragment.newInstance(), "register")
            .addToBackStack(null)
            .commit();
    }

    public static StartFragment newInstance() {
        return new StartFragment();
    }

    public StartFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_start, container, false);
        ButterKnife.bind(this, view);
        anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_down);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        return view;
    }
}
