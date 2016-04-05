package com.aryamohades.tablemate.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.Table;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.UserService;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TableFragment extends BaseFragment {
    @Bind(R.id.restaurant_name) TextView restaurantNameText;
    @Bind(R.id.server_name) TextView serverNameText;
    @Bind(R.id.party_size) TextView partySizeText;

    public static TableFragment newInstance() {
        return new TableFragment();
    }

    public TableFragment() {
    }

    @OnClick(R.id.order)
    public void order() {
        final OrderFragment fragment = OrderFragment.newInstance();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment, "order")
                .addToBackStack(null)
                .commit();
    }

    @OnClick(R.id.request)
    public void requestService() {
        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
            .makeRequest(token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Response<Void>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    Log.e("TableActivity", e.getMessage());
                }

                @Override
                public void onNext(Response<Void> res) {
                    if (res.isSuccess()) {
                        Toast.makeText(getActivity(), "Request made", Toast.LENGTH_LONG).show();
                    } else if (res.code() == 409) {
                        // TODO: request already made
                    }
                }
            });
    }

    @OnClick(R.id.view_check)
    public void goToPayment() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, PayFragment.newInstance(), "pay")
                .addToBackStack(null)
            .commit();
    }

    private void updateTableView(Table table) {
        final String partySize = table.getSize() + " Tablemates";
        final String serverName = "Your server is " + table.getServer().getFirstName();
        final String restaurantName = table.getRestaurant().getName();
        restaurantNameText.setText(restaurantName);
        serverNameText.setText(serverName);
        partySizeText.setText(partySize);

        if (table.hasRequested()) {
            // TODO: Display request made
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
            .getTable(token)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Response<Table>>() {
                @Override
                public void onCompleted() {
                    // Not implemented
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("Table", e.getMessage());
                }

                @Override
                public void onNext(Response<Table> res) {
                    Table table = res.body();
                    updateTableView(table);
                }
            });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_table, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Tablemate", R.drawable.menu);
        updateTableView(getTable());
        return view;
    }
}
