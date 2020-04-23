package com.example.bank.Frontend;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.bank.Models.Account;
import com.example.bank.Models.Transaction;
import com.example.bank.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawFragment extends Fragment {
    View view;
    TextView welcomeText, withdrawAmount, withdrawText;
    SeekBar withdrawSeekBar;
    Button withdraw;
    private AccountViewModel accountViewModel;
    Transaction transaction;
    Account account;
    List<Transaction> transactions;
    double balance = 0.0;
    int value = 0;
    public WithdrawFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.withdraw_fragment, container, false);
        return view;
    }
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        welcomeText = this.view.findViewById(R.id.withdraw_welcome);
        withdrawAmount = this.view.findViewById(R.id.withdrawAmount);
        withdrawText = this.view.findViewById(R.id.withdraw_text);
        withdrawSeekBar = this.view.findViewById(R.id.seekBarWithdraw);
        withdraw = this.view.findViewById(R.id.button_withdraw);
        Bundle bundle = getArguments();
        this.account = (Account) bundle.getSerializable("account");
        assert account != null;
        welcomeText.setText(account.toString());
        balance = account.getBalance();
        value = (int) balance;
        withdrawSeekBar.setMax(value);
        this.transactions = getTransactions(account.getId());
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Transaction> newTransactions = withdraw(Double.parseDouble(withdrawAmount.getText().toString()));
                transactions.addAll(newTransactions);
                account.setTransactionList(transactions);
                accountViewModel.updateAccount(account);
                welcomeText.setText(account.toString());
                Snackbar.make(v,withdrawAmount.getText().toString() + " withdrawn from account",
                        Snackbar.LENGTH_SHORT).show();
                balance = account.getBalance();
                value = (int) balance;
                withdrawSeekBar.setMax(value);
                withdrawSeekBar.setProgress(0);
                Bundle bundle = new Bundle();
                Account transferAcc = account;
                bundle.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_withdraw_fragment_to_account_fragment, bundle);
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        controller.popBackStack(R.id.account_fragment, false);
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
}
