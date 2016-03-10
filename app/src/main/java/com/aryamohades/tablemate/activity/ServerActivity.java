package com.aryamohades.tablemate.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.adapter.TableAdapter;
import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.model.User;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.TableService;
import com.aryamohades.tablemate.utils.PreferencesManager;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServerActivity extends AppCompatActivity {
    private PreferencesManager prefs;
    private User user;
    private TableAdapter tableAdapter;

    @Bind(R.id.tables_view) RecyclerView tablesView;

    @Override
    protected void onResume() {
        super.onResume();

        TableService service = ServiceFactory.createService(TableService.class, TableService.API_BASE);
        service.getServerTables(user.getAccessToken())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<ArrayList<Table>>() {
                @Override
                public void onCompleted() {
                    // not implemented
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Server Activity", e.getMessage());
                }

                @Override
                public void onNext(ArrayList<Table> tables) {
                    tableAdapter.setData(tables);
                }
            });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        ButterKnife.bind(this);
        prefs = new PreferencesManager(this);
        user = prefs.getUser();
        tableAdapter = new TableAdapter();
        tablesView.setAdapter(tableAdapter);
    }
}
