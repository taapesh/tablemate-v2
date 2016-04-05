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
import com.aryamohades.tablemate.adapter.RestaurantAdapter;
import com.aryamohades.tablemate.model.Restaurant;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.UserService;
import com.aryamohades.tablemate.utils.ItemClickSupport;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomeRestaurantsFragment extends BaseFragment {
    private RestaurantAdapter adapter;

    @Bind(R.id.restaurants_view) RecyclerView restaurantsView;

    public static HomeRestaurantsFragment newInstance() {
        return new HomeRestaurantsFragment();
    }

    public HomeRestaurantsFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
            .getNearbyRestaurants(token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<ArrayList<Restaurant>>() {
                @Override
                public void onCompleted() {
                    // not implemented
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("HomeRestaurants", e.getMessage());
                }

                @Override
                public void onNext(ArrayList<Restaurant> restaurants) {
                    adapter.setData(restaurants);
                }
            });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home_restaurants, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Tablemate", R.drawable.menu);
        restaurantsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RestaurantAdapter();
        restaurantsView.setAdapter(adapter);

        ItemClickSupport.addTo(restaurantsView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView rv, int position, View v) {
                        final Restaurant r = adapter.getItem(position);
                        final RestaurantDetailFragment restaurantDetail =
                                RestaurantDetailFragment.newInstance(r.getId(), r.getName(), r.getAddress());

                        getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, restaurantDetail, "restaurantDetail")
                            .addToBackStack(null)
                            .commit();
                    }
                }
        );
        return view;
    }
}
