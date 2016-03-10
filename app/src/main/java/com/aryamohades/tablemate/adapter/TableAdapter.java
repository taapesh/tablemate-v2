package com.aryamohades.tablemate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.Table;

import java.util.ArrayList;

import butterknife.ButterKnife;

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
        // viewHolder.size.setText(table.getSize());
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // @Bind(R.id.login) TextView login;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            // login = (TextView) itemView.findViewById(R.id.login);
        }
    }
}
