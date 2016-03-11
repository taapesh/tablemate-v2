package com.aryamohades.tablemate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.model.User;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.TableService;
import com.aryamohades.tablemate.utils.PreferencesManager;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class CustomerHomeActivity extends AppCompatActivity {
    private final String ADDRESS = "1234 Restaurant St.";
    private final String NAME = "Awesome Restaurant";
    private final String NUMBER = "1";
    private PreferencesManager prefs;
    private User user;

    @Bind(R.id.btn_create_table) Button createTableBtn;

    @OnClick(R.id.btn_create_table)
    public void createTable() {
        TableService service = ServiceFactory.createService(TableService.class, TableService.API_BASE);
        service.createOrJoinTable("Token " + user.getAccessToken(), NAME, ADDRESS, NUMBER)
            .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Table>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Customer Home Activity", e.getMessage());
                    }

                    @Override
                    public void onNext(Table table) {
                        System.out.println("Table creation success");
                        System.out.println("Table size: " + table.getTableSize());
                        Intent i = new Intent(CustomerHomeActivity.this, TableActivity.class);
                        i.putExtra(Table.EXTRA_NAME, table);
                        startActivity(i);
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
        ButterKnife.bind(this);
        prefs = new PreferencesManager(this);
        user = prefs.getUser();
    }
}
