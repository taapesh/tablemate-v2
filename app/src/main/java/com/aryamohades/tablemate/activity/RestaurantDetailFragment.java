package com.aryamohades.tablemate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.UserService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RestaurantDetailFragment extends BaseFragment {
    @Bind(R.id.name) TextView name;
    @Bind(R.id.address) TextView address;

    private String rId;

    public static RestaurantDetailFragment newInstance(String rId, String rName, String rAddr) {
        final RestaurantDetailFragment fragment = new RestaurantDetailFragment();
        final Bundle args = new Bundle();
        args.putString("restaurant_id", rId);
        args.putString("restaurant_name", rName);
        args.putString("restaurant_addr", rAddr);
        fragment.setArguments(args);
        return fragment;
    }

    public RestaurantDetailFragment() {
    }

    @OnClick(R.id.view_menu)
    public void viewMenu() {
        MenuFragment fragment = MenuFragment.newInstance(rId);

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, "menu")
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.join_table)
    public void createOrJoinTable() {
        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
                .createOrJoinTable(token, rId, "17")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Table>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("CreateTable", e.getMessage());
                    }

                    @Override
                    public void onNext(Table table) {
                        Intent i = new Intent(getActivity(), TableActivity.class);
                        prefs.setTable(table);
                        startActivity(i);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);
        ButterKnife.bind(this, view);
        final Bundle args = getArguments();
        final String restaurantName = args.getString("restaurant_name");
        setupToolbar(restaurantName, R.drawable.back_arrow);
        name.setText(restaurantName);
        address.setText(args.getString("restaurant_addr"));
        this.rId = args.getString("restaurant_id");
        return view;
    }
}
