package com.aryamohades.tablemate.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.adapter.ReceiptAdapter;
import com.aryamohades.tablemate.model.Receipt;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.UserService;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ReceiptsFragment extends BaseFragment {
    private ReceiptAdapter adapter;
    @Bind(R.id.receipts_view) RecyclerView receiptsView;

    public static ReceiptsFragment newInstance() {
        return new ReceiptsFragment();
    }

    public ReceiptsFragment() {
    }


    public void getReceipts() {
        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
                .getReceipts(token)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Receipt>>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ReceiptsActivity", e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Receipt> receipts) {
                        adapter.setData(receipts);
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new ReceiptAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_receipts, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Receipts", R.drawable.back_arrow);
        receiptsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        receiptsView.setAdapter(adapter);
        getReceipts();
        return view;
    }

}
