package com.example.bank.Frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bank.Models.Bank;
import com.example.bank.R;

import java.util.List;

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankViewHolder> {

    class BankViewHolder extends RecyclerView.ViewHolder {
        private final TextView bankItemView;

        private BankViewHolder(View itemView) {
            super(itemView);
            bankItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Bank> mBanks; // cached copy of banks, later implement the same with other classes

    BankListAdapter(Context context){mInflater = LayoutInflater.from(context);}

    @Override
    public BankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent,false);
        return new BankViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BankViewHolder holder, int position) {
        if (mBanks != null) {
            Bank current = mBanks.get(position);
            holder.bankItemView.setText(current.toString());
        } else {
            // If there is no data or the data is not yet ready
            holder.bankItemView.setText("No banks available");
        }
    }

    void setBanks(List<Bank> banks) {
        mBanks = banks;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mBanks != null) {
            return mBanks.size();
        } else {
            return 0;
        }
    }
}
