package com.aryamohades.tablemate.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.HttpResponse;
import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.model.User;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.TableService;
import com.aryamohades.tablemate.utils.PreferencesManager;

import butterknife.ButterKnife;
import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TableActivity extends AppCompatActivity {
    private User user;

    @Bind(R.id.restaurantNameText) TextView tvRestaurantName;
    @Bind(R.id.serverNameText) TextView tvServerName;
    @Bind(R.id.partySizeText) TextView tvPartySize;

    @Bind(R.id.viewCheckBtn) Button viewCheckBtn;
    @Bind(R.id.requestServiceBtn) Button requestServiceBtn;
    @Bind(R.id.orderBtn) Button orderBtn;

    @OnClick(R.id.orderBtn)
    public void goToMenu() {
        Intent i = new Intent(TableActivity.this, MenuActivity.class);
        startActivity(i);
    }

    @OnClick(R.id.requestServiceBtn)
    public void requestService() {
        TableService service = ServiceFactory.createService(TableService.class, TableService.API_BASE);
        service.requestService("Token " + user.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<HttpResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Table Activity", e.getMessage());
                    }

                    @Override
                    public void onNext(Response<HttpResponse> res) {
                        if (res.isSuccess()) {
                            System.out.println(res.body().getMessage());
                            Toast.makeText(TableActivity.this, "Request made", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @OnClick(R.id.viewCheckBtn)
    public void goToPayment() {
        Intent i = new Intent(TableActivity.this, PaymentActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TableService service = ServiceFactory.createService(TableService.class, TableService.API_BASE);
        service.getActiveTable("Token " + user.getAccessToken())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Table>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Table Activity", e.getMessage());
                    }

                    @Override
                    public void onNext(Table table) {
                        Log.d("Table Activity", "Table retrieved");
                        // TODO: X requested service Ym ago
                    }
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        ButterKnife.bind(this);
        user = new PreferencesManager(this).getUser();
        Table table = getIntent().getParcelableExtra(Table.EXTRA_NAME);
        if (table != null) {
            tvRestaurantName.setText(table.getRestaurantName());
            tvServerName.setText("Your server is " + table.getServerName());
            tvPartySize.setText(table.getTableSize() + " Tablemates");
        }
    }
}
