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
import android.widget.TextView;
import android.widget.Toast;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.adapter.PendingOrderAdapter;
import com.aryamohades.tablemate.model.Order;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.UserService;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class OrderFragment extends BaseFragment {
    @Bind(R.id.total) TextView totalView;
    @Bind(R.id.orders_view) RecyclerView pendingOrdersView;
    private PendingOrderAdapter adapter;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    public OrderFragment() {
    }

    @OnClick(R.id.place_order)
    public void placeOrder() {
        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
            .placeOrder(token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Response<Void>>() {
                @Override
                public void onCompleted() {
                    // not implemented
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("PlaceOrder", e.getMessage());
                }

                @Override
                public void onNext(Response<Void> res) {
                    if (res.code() == 200) {
                        Toast.makeText(getActivity(), "Order placed", Toast.LENGTH_SHORT).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    } else {
                        // TODO: error placing order
                    }
                }
            });
    }

    @OnClick(R.id.add_item)
    public void addItem() {
        getActivity().getSupportFragmentManager().beginTransaction()
            .replace(R.id.content, AddItemFragment.newInstance(), "addItem")
            .addToBackStack(null)
            .commit();
    }

    @Override
    public void onResume() {
        super.onResume();

        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
            .getPendingOrders(token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<ArrayList<Order>>() {
                @Override
                public void onCompleted() {
                    // not implemented
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("PendingOrders", e.getMessage());
                }

                @Override
                public void onNext(ArrayList<Order> orders) {
                    adapter.setData(orders);

                    Double total = 0.0;
                    for (Order o : orders) {
                        total += o.getItem().getPrice();
                    }
                    final String totalText = "$" + String.format("%.2f", total);
                    totalView.setText(totalText);
                }
            });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new PendingOrderAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_order, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Order", R.drawable.back_arrow);
        pendingOrdersView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pendingOrdersView.setAdapter(adapter);
        return view;
    }
}
