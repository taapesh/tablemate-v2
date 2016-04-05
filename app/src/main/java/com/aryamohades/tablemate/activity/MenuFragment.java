package com.aryamohades.tablemate.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.adapter.MenuItemAdapter;
import com.aryamohades.tablemate.model.MenuItem;
import com.aryamohades.tablemate.service.RestaurantService;
import com.aryamohades.tablemate.service.ServiceFactory;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MenuFragment extends BaseFragment {
    @Bind(R.id.menu_view) RecyclerView itemsView;
    private MenuItemAdapter adapter;
    private String rId;

    public static MenuFragment newInstance(String rId) {
        final Bundle args = new Bundle();
        args.putString("restaurant_id", rId);
        final MenuFragment fragment = new MenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MenuFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Menu", R.drawable.back_arrow);
        itemsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MenuItemAdapter();
        itemsView.setAdapter(adapter);
        rId = getArguments().getString("restaurant_id");
        getMenuItems();
        return view;
    }

    private void getMenuItems() {
        ServiceFactory.createService(RestaurantService.class, RestaurantService.ENDPOINT)
            .getMenu(rId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<ArrayList<MenuItem>>() {
                @Override
                public void onCompleted() {
                    // not implemented
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Menu", e.getMessage());
                }

                @Override
                public void onNext(ArrayList<MenuItem> menuItems) {
                    adapter.setData(menuItems);
                }
            });
    }
}
