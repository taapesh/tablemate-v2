package com.aryamohades.tablemate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.Restaurant;

import java.util.ArrayList;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {
    private ArrayList<Restaurant> restaurants;

    public RestaurantAdapter() {
        super();
        restaurants = new ArrayList<Restaurant>();
    }

    public void setData(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
        notifyDataSetChanged();
    }

    public Restaurant getItem(int position) {
        return restaurants.get(position);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.restaurant_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Restaurant restaurant = restaurants.get(i);
        viewHolder.name.setText(restaurant.getName());
        viewHolder.address.setText(restaurant.getAddress());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView address;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            address = (TextView) view.findViewById(R.id.address);
        }
    }
}
