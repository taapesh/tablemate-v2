package com.aryamohades.tablemate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aryamohades.tablemate.R;
import com.aryamohades.tablemate.model.Receipt;

import java.util.ArrayList;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
    private ArrayList<Receipt> receipts;

    public ReceiptAdapter() {
        super();
        receipts = new ArrayList<Receipt>();
    }

    public void setData(ArrayList<Receipt> receipts) {
        this.receipts = receipts;
        notifyDataSetChanged();
    }

    public Receipt getItem(int position) {
        return receipts.get(position);
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.receipt_view, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final Receipt receipt = receipts.get(i);
        viewHolder.restaurantName.setText(receipt.getRestaurant().getName());
        final String billText = "$" + String.format("%.2f", receipt.getTotal());
        viewHolder.bill.setText(billText);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;
        TextView bill;

        public ViewHolder(View view) {
            super(view);
            bill = (TextView) view.findViewById(R.id.bill);
            restaurantName = (TextView) view.findViewById(R.id.name);
        }
    }
}
