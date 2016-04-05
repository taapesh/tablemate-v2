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
import com.aryamohades.tablemate.adapter.TableAdapter;
import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.service.ServerService;
import com.aryamohades.tablemate.service.ServiceFactory;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerTablesFragment extends BaseFragment {
    private String rId;
    private TableAdapter adapter;
    @Bind(R.id.tables_view) RecyclerView tablesView;

    public static ServerTablesFragment newInstance() {
        return new ServerTablesFragment();
    }

    public ServerTablesFragment() {
    }

    private void getServerTables() {
        ServiceFactory.createService(ServerService.class, ServerService.ENDPOINT)
                .getTables(token, rId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<Table>>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("ServerTables", e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<Table> tables) {
                        adapter.setData(tables);
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        getServerTables();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        adapter = new TableAdapter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_server_tables, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Tablemate", R.drawable.menu);
        tablesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tablesView.setAdapter(adapter);
        rId = getRestaurantId();
        return view;
    }
}
