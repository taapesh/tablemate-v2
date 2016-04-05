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
import com.aryamohades.tablemate.service.ServerService;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.utils.PreferencesManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerRestaurantDetailFragment extends BaseFragment {
    @Bind(R.id.name) TextView name;
    @Bind(R.id.address) TextView address;

    private String rId;

    public static ServerRestaurantDetailFragment newInstance(String rId, String rName, String rAddr) {
        final ServerRestaurantDetailFragment fragment = new ServerRestaurantDetailFragment();
        final Bundle args = new Bundle();
        args.putString("restaurant_id", rId);
        args.putString("restaurant_name", rName);
        args.putString("restaurant_addr", rAddr);
        fragment.setArguments(args);
        return fragment;
    }

    public ServerRestaurantDetailFragment() {
    }

    @OnClick(R.id.start_serving)
    public void startServing() {
        ServiceFactory.createService(ServerService.class, ServerService.ENDPOINT)
                .startServing(token, rId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<Void>>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("RestaurantDetail", e.getMessage());
                    }

                    @Override
                    public void onNext(Response<Void> res) {
                        if (res.isSuccess()) {
                            final PreferencesManager prefs = new PreferencesManager(getActivity());
                            prefs.setIsServing(true);
                            prefs.setRestaurantId(rId);
                            Intent i = new Intent(getActivity(), ServerActivity.class);
                            startActivity(i);
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_server_restaurant_detail, container, false);
        ButterKnife.bind(this, view);
        final Bundle args = getArguments();
        final String restaurantName = args.getString("restaurant_name");
        setupToolbar(restaurantName, R.drawable.back_arrow);
        name.setText(restaurantName);
        address.setText(args.getString("restaurant_addr"));
        rId = args.getString("restaurant_id");
        return view;
    }
}
