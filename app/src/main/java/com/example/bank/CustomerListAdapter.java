package com.example.bank;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {

    class CustomerViewHolder extends RecyclerView.ViewHolder {
        private final TextView customerItemView;

        private CustomerViewHolder(View itemView) {
            super(itemView);
            customerItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Customer> mCustomers;

    CustomerListAdapter(Context context) {mInflater = LayoutInflater.from(context);}

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        if (mCustomers != null) {
            Customer customer = mCustomers.get(position);
            holder.customerItemView.setText(customer.toString());
        } else {
            // If no customer data is available
            holder.customerItemView.setText("No Customers available");
        }
    }

    void setCustomers(List<Customer> customers) {
        mCustomers = customers;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mCustomers != null) {
            return mCustomers.size();
        } else {
            return 0;
        }
    }
}
