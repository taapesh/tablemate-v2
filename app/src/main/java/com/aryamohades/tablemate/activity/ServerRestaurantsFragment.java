package com.aryamohades.tablemate.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.adapter.RestaurantAdapter;
import com.aryamohades.tablemate.model.Restaurant;
import com.aryamohades.tablemate.service.ServerService;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.utils.ItemClickSupport;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerRestaurantsFragment extends BaseFragment {
    private RestaurantAdapter adapter;
    @Bind(R.id.restaurants_view) RecyclerView restaurantsView;

    public static ServerRestaurantsFragment newInstance() {
        return new ServerRestaurantsFragment();
    }

    public ServerRestaurantsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        ServiceFactory.createService(ServerService.class, ServerService.ENDPOINT)
                .getRestaurants(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Restaurant>>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ServerRestaurants", e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Restaurant> restaurants) {
                        adapter.setData(restaurants);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new RestaurantAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_server_restaurants, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Tablemate", R.drawable.menu);
        restaurantsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        restaurantsView.setAdapter(adapter);

        ItemClickSupport.addTo(restaurantsView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView rv, int position, View v) {
                        final Restaurant r = adapter.getItem(position);
                        final ServerRestaurantDetailFragment serverRestaurantDetail =
                                ServerRestaurantDetailFragment.newInstance(r.getId(), r.getName(), r.getAddress());

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content, serverRestaurantDetail)
                                .addToBackStack(null)
                                .commit();
                    }
                }
        );
        return view;
    }
}
