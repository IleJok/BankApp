package com.example.bank.Frontend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.Models.Account;
import com.example.bank.R;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.util.List;
/* Custom LIST adapter to show customers accounts in recyclerview and items are clickable.
* when item is clicked, user is navigated to that account and AccountFragment is opened*/
public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.AccountViewHolder> {
    private List<Account> mCustomersAccounts;
    private static ClickListener clickListener;

    class AccountViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView accountItemView;

        private AccountViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnClickListener(this);
            accountItemView = itemView.findViewById(R.id.textView);
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

    AccountListAdapter(List<Account> accounts) {
        mCustomersAccounts = accounts;
    }
    void setOnItemClickListener(ClickListener clickListener) {
        AccountListAdapter.clickListener = clickListener;
    }

    Account getItem(int position) {
        return mCustomersAccounts.get(position);
    }

    @NotNull
    @Override
    public AccountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new AccountViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull AccountViewHolder holder, int position) {
        if (mCustomersAccounts != null) {
            Account account = mCustomersAccounts.get(position);
            // account toString is defined in the Account Model
            holder.accountItemView.setText(account.toString());
        } else {
            // If no account data is available
            holder.accountItemView.setText(R.string.no_accounts);
        }
    }

    /*This is an important function as it lets us to notify if there is changes in our list of
    * accounts*/
    void setAccounts(List<Account> customersAccounts) {
        this.mCustomersAccounts = customersAccounts;
        notifyDataSetChanged();
    }


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
