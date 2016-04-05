package com.aryamohades.tablemate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.MenuItem;

import java.util.ArrayList;

public class MenuItemAdapter extends RecyclerView.Adapter<MenuItemAdapter.ViewHolder> {
    private ArrayList<MenuItem> items;

    public MenuItemAdapter() {
        super();
        items = new ArrayList<MenuItem>();
    }

    public void setData(ArrayList<MenuItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public MenuItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_item_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final MenuItem item = items.get(i);
        viewHolder.name.setText(item.getName());
        viewHolder.price.setText(String.format("%.2f", item.getPrice()));
        viewHolder.description.setText(item.getDescription());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView price;
        TextView description;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            description = (TextView) view.findViewById(R.id.description);
        }
    }
}
