package com.example.bank;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/* Custom LIST adapter to show transactions in recyclerview */
public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder> {
    private List<Transaction> mAccountsTransactions;
    private static ClickListener transactionClickListener;

    class TransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView transactionItemView;

        private TransactionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnClickListener(this);
            transactionItemView = itemView.findViewById(R.id.textView); // TODO make this correct
        }
        @Override
        public void onClick(View v) {
            transactionClickListener.onItemClick(getAdapterPosition(), v);
            Transaction transaction = mAccountsTransactions.get(getAdapterPosition());
            Snackbar.make(v, transaction.toString(),
                    Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            transactionClickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }
    public TransactionListAdapter(List<Transaction> transactions) {
        this.mAccountsTransactions = transactions;
    }
    public void setOnItemClickListener(ClickListener transactionClickListener) {
        TransactionListAdapter.transactionClickListener = transactionClickListener;
    }

    public Transaction getItem(int position) {
        return mAccountsTransactions.get(position);
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        if (mAccountsTransactions != null) {
            Transaction transaction = mAccountsTransactions.get(position);
            holder.transactionItemView.setText(transaction.toString());
        } else {
            // If no customer data is available
            holder.transactionItemView.setText("No Accounts available");
        }
    }


    void setTransactions(List<Transaction> accountsTransactions) {
        if (this.mAccountsTransactions.size() > 0)
            this.mAccountsTransactions.clear();
        this.mAccountsTransactions = accountsTransactions;
        notifyDataSetChanged();
    }


    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }

    @Override
    public int getItemCount() {
        if (mAccountsTransactions != null) {
            return mAccountsTransactions.size();
        } else {

            return 0;
        }
    }
}
