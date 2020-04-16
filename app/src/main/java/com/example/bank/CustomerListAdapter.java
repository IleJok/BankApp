package com.example.bank;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.CustomerViewHolder> {
    private List<Account> mCustomersAccounts;
    private static ClickListener clickListener;

    class CustomerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView customerItemView;

        private CustomerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnClickListener(this);
            customerItemView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void onClick(View v) {
           clickListener.onItemClick(getAdapterPosition(), v);
            Account account = mCustomersAccounts.get(getAdapterPosition());
            Snackbar.make(v, account.toString(),
                    Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public CustomerListAdapter(List<Account> accounts) {
        mCustomersAccounts = accounts;
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        CustomerListAdapter.clickListener = clickListener;
    }

    @Override
    public CustomerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new CustomerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomerViewHolder holder, int position) {
        System.out.println("ONko mitään!!! " + mCustomersAccounts.size());
        if (mCustomersAccounts != null) {
            Account account = mCustomersAccounts.get(position);
            holder.customerItemView.setText(account.toString());
            System.out.println("Tullaanko koskaan tänne!?");
        } else {
            System.out.println("Tullaanko koskaan tai edes tänne!?");
            // If no customer data is available
            holder.customerItemView.setText("No Accounts available");
        }
    }

/*
    void setAccounts(LiveData<List<Account>> customersAccounts) {
        System.out.println("Tullaaaaaaaaaaaaaaaaaaanko");
        this.mCustomersAccounts = customersAccounts;
        notifyDataSetChanged();
    }
*/

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }
    @Override
    public int getItemCount() {
        if (mCustomersAccounts != null) {
            return mCustomersAccounts.size();
        } else {

            return 0;
        }
    }
}
