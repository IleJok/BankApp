package com.example.bank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountViewHolder> {
    private List<Account> mCustomersAccounts;
    private static ClickListener clickListener;

    class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView customerItemView;

        private AccountViewHolder(View itemView) {
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

    public AccountListAdapter(List<Account> accounts) {
        mCustomersAccounts = accounts;
    }
    public void setOnItemClickListener(ClickListener clickListener) {
        AccountListAdapter.clickListener = clickListener;
    }

    public Account getItem(int position) {
        return mCustomersAccounts.get(position);
    }

    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AccountViewHolder holder, int position) {
        if (mCustomersAccounts != null) {
            Account account = mCustomersAccounts.get(position);
            holder.customerItemView.setText(account.toString());
        } else {
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
