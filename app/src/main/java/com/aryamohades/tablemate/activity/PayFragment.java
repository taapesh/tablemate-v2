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

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.adapter.UserOrderAdapter;
import com.aryamohades.tablemate.model.Order;
import com.aryamohades.tablemate.model.Receipt;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.UserService;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PayFragment extends BaseFragment {
    private UserOrderAdapter adapter;
    @Bind(R.id.orders_view) RecyclerView orders;
    @Bind(R.id.total) TextView totalTextView;

    public static PayFragment newInstance() {
        return new PayFragment();
    }

    public PayFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new UserOrderAdapter();
    }

    private void getUserOrders() {
        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
                .getActiveOrders(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Order>>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("PaymentActivity", e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Order> orders) {
                        adapter.setData(orders);

                        Double total = 0.0;
                        for (Order o : orders) {
                            total += o.getItem().getPrice();
                        }
                        final String totalText = "$" + String.format("%.2f", total);
                        totalTextView.setText(totalText);
                    }
                });
    }

    @OnClick(R.id.pay)
    public void pay() {
        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
                .pay(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Receipt>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("PaymentActivity", e.getMessage());
                    }

                    @Override
                    public void onNext(Receipt receipt) {
                        Intent i = new Intent(getActivity(), HomeActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_pay, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Pay", R.drawable.back_arrow);
        orders.setLayoutManager(new LinearLayoutManager(getActivity()));
        orders.setAdapter(adapter);
        getUserOrders();
        return view;
    }
}
