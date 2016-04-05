package com.aryamohades.tablemate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.Table;

import java.util.ArrayList;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.ViewHolder> {
    private ArrayList<Table> tables;

    public TableAdapter() {
        super();
        tables = new ArrayList<Table>();
    }

    public void addData(Table table) {
        tables.add(table);
        notifyDataSetChanged();
    }

    public void setData(ArrayList<Table> tables) {
        this.tables.clear();
        this.tables.addAll(tables);
        notifyDataSetChanged();
    }

    public void clear() {
        tables.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tables.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.table_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Table table = tables.get(i);
        viewHolder.tableNumber.setText(table.getTableNumber());
        viewHolder.partySize.setText(""+table.getSize());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tableNumber;
        TextView partySize;

        public ViewHolder(View view) {
            super(view);
            tableNumber = (TextView) view.findViewById(R.id.table_number);
            partySize = (TextView) view.findViewById(R.id.party_size);
        }
    }
}
