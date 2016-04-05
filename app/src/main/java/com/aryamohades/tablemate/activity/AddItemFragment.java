package com.aryamohades.tablemate.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.aryamohades.tablemate.adapter.MenuItemAdapter;
import com.aryamohades.tablemate.model.MenuItem;
import com.aryamohades.tablemate.model.Order;
import com.aryamohades.tablemate.service.RestaurantService;
import com.aryamohades.tablemate.service.ServiceFactory;
import com.aryamohades.tablemate.service.UserService;
import com.aryamohades.tablemate.utils.ItemClickSupport;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddItemFragment extends BaseFragment {
    @Bind(R.id.menu_view) RecyclerView itemsView;
    private MenuItemAdapter adapter;

    public static AddItemFragment newInstance() {
        return new AddItemFragment();
    }

    public AddItemFragment() {
    }

    private void showOrderDialog(final MenuItem item) {
        View prompt = LayoutInflater.from(getActivity()).inflate(R.layout.order_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setView(prompt);

        final TextView name = (TextView) prompt.findViewById(R.id.name);
        final TextView price = (TextView) prompt.findViewById(R.id.price);
        final TextView description = (TextView) prompt.findViewById(R.id.description);

        name.setText(item.getName());
        price.setText(String.format("%.2f", item.getPrice()));
        description.setText(item.getDescription());


        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                addItemToOrder(item);
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void addItemToOrder(MenuItem item) {
        ServiceFactory.createService(UserService.class, UserService.ENDPOINT)
            .addToOrder(token, item.getId())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<Order>() {
                @Override
                public void onCompleted() {
                    // not implemented
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("MenuActivity", e.getMessage());
                }

                @Override
                public void onNext(Order order) {
                    Toast.makeText(getActivity(), "Added item to order", Toast.LENGTH_SHORT).show();
                    getActivity().getSupportFragmentManager().popBackStack();
                }
            });
    }

    private void getMenuItems() {
        ServiceFactory.createService(RestaurantService.class, RestaurantService.ENDPOINT)
                .getMenu(getCurrentRestaurantId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayList<MenuItem>>() {
                    @Override
                    public void onCompleted() {
                        // not implemented
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("AddItem", e.getMessage());
                    }

                    @Override
                    public void onNext(ArrayList<MenuItem> menuItems) {
                        adapter.setData(menuItems);
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        ButterKnife.bind(this, view);
        setupToolbar("Add Item", R.drawable.back_arrow);
        itemsView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MenuItemAdapter();
        itemsView.setAdapter(adapter);
        getMenuItems();

        ItemClickSupport.addTo(itemsView).setOnItemClickListener(
                new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView rv, int position, View v) {
                        MenuItem item = adapter.getItem(position);
                        showOrderDialog(item);
                    }
                }
        );
        return view;
    }
}
