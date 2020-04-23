package com.example.bank;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    View view;
    TextView welcomeText, depositAmount, withdrawAmount, depositText, withdrawText;
    SeekBar depositSeekBar, withdrawSeekBar;
    Button editAccount, deposit, withdraw;
    double balance = 0.0;
    int value = 0;
    private AccountViewModel accountViewModel;
    Transaction transaction;
    Account account;
    List<Transaction> transactions;
    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.account_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        welcomeText = this.view.findViewById(R.id.accountViewText);
        depositAmount = this.view.findViewById(R.id.depositAmount);
        withdrawAmount = this.view.findViewById(R.id.withdrawAmount);
        depositText = this.view.findViewById(R.id.deposit_text);
        withdrawText = this.view.findViewById(R.id.withdraw_text);
        depositSeekBar = this.view.findViewById(R.id.seekBarDeposit);
        withdrawSeekBar = this.view.findViewById(R.id.seekBarWithdraw);
        editAccount = this.view.findViewById(R.id.button_edit_account);
        deposit = this.view.findViewById(R.id.button_deposit);
        withdraw = this.view.findViewById(R.id.button_withdraw);
        Bundle bundle = getArguments();
        this.account = (Account) bundle.getSerializable("account");
        assert account != null;
        System.out.println("Account id " + account.getId());
        this.transactions = getTransactions(account.getId());
        welcomeText.setText(account.toString());
        balance = account.getBalance();
        value = (int) balance;
        withdrawSeekBar.setMax(value);
        RecyclerView recyclerView = this.view.findViewById(R.id.transaction_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        final TransactionListAdapter transactionListAdapter = new TransactionListAdapter(this.transactions);
        recyclerView.setAdapter(transactionListAdapter);
        transactionListAdapter.setOnItemClickListener(new TransactionListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick position: " + position);
            }
            @Override
            public void onItemLongClick(int position, View v) {
                Log.d(TAG, "onItemClick position: " + position);
            }
        });

        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                Account transferAcc = account;
                bundle1.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_accountFragment_to_modifyAccountFragment, bundle1);
            }
        });

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Transaction> newTransactions = deposit(Double.parseDouble(depositAmount.getText().toString()));
                /*getTransactions(account.getId());*/
                transactions.addAll(newTransactions);
                transactionListAdapter.setTransactions(newTransactions);
                int count = transactionListAdapter.getItemCount();
                System.out.println("Paljon countti on" + count);
                transactionListAdapter.notifyItemChanged(count);
                welcomeText.setText(account.toString());
                Snackbar.make(v,depositAmount.getText().toString() + " added to account",
                        Snackbar.LENGTH_SHORT).show();
                balance = account.getBalance();
                value = (int) balance;
                withdrawSeekBar.setMax(value);
                depositSeekBar.setProgress(0);



            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Transaction> transactions = withdraw(Double.parseDouble(withdrawAmount.getText().toString()));
                welcomeText.setText(account.toString());
                Snackbar.make(v,withdrawAmount.getText().toString() + " withdrawn from account",
                        Snackbar.LENGTH_SHORT).show();
                balance = account.getBalance();
                value = (int) balance;
                withdrawSeekBar.setMax(value);
                withdrawSeekBar.setProgress(0);
                transactionListAdapter.setTransactions(transactions);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        controller.popBackStack(R.id.profile_fragment, false);
                    }
                });

        depositSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             int amountToDeposit = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amountToDeposit = progress;
                depositAmount.setText(Integer.toString(amountToDeposit));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                depositAmount.setText(Integer.toString(amountToDeposit));
            }
        });

        withdrawSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int amountToWithdraw = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amountToWithdraw = progress;
                withdrawAmount.setText(Integer.toString(amountToWithdraw));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                withdrawAmount.setText(Integer.toString(amountToWithdraw));
            }
        });
    }

    public void insertTransactions(Account account) {
        accountViewModel.insertTransactions(account);
    }

    public List<Transaction> getTransactions(int id) {
        try {
            this.transactions = accountViewModel.getTransactionsList(id);
            account.setTransactionList(this.transactions);
            return account.getTransactionList();
        } catch (Exception e) {
            System.out.println("Erroro " + e);
        }
        return this.transactions;
    }

    public List<Transaction> deposit(Double amount) {
        System.out.println("Deposit account" + account.toString());
        if (amount > 0) {
            try {
                this.transaction = this.account.deposit(amount);
                //this.account.addToTransactionList(transaction);
                accountViewModel.insertTransaction(this.transaction);
                List<Transaction> newTransactions = accountViewModel.getTransactionsList(this.account.getId());
                account.setTransactionList(newTransactions);
                return newTransactions;
            } catch (Exception e) {
                throw e;
            }

        }
        return account.getTransactionList();
    }

    public List<Transaction> withdraw(Double amount) {
        System.out.println("Deposit account" + account.toString());
        if (amount < this.account.getBalance()) {
            try {
                this.transaction = this.account.withdraw(amount);
                //this.account.addToTransactionList(transaction);
                accountViewModel.insertTransaction(this.transaction);
                List<Transaction> newTransactions = accountViewModel.getTransactionsList(this.account.getId());
                account.setTransactionList(newTransactions);
                return  newTransactions;
            } catch (Exception e) {
                throw e;
            }

        }
        return account.getTransactionList();
    }

}
