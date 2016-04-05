package com.aryamohades.tablemate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.Order;

import java.util.ArrayList;

public class UserOrderAdapter extends RecyclerView.Adapter<UserOrderAdapter.ViewHolder> {
    private ArrayList<Order> orders;

    public UserOrderAdapter() {
        super();
        orders = new ArrayList<Order>();
    }

    public void setData(ArrayList<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    public Order getItem(int position) {
        return orders.get(position);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_order_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Order order = orders.get(i);
        viewHolder.name.setText(order.getItem().getName());
        final String priceText = "$" + String.format("%.2f", order.getItem().getPrice());
        viewHolder.price.setText(priceText);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
        }
    }
}

