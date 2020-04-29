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
 * Fragment to deposit money to an account. Fragment is accessed from AccountFragment.
 * Layout file is deposit_fragment.xml
 */
public class DepositFragment extends Fragment {
    private View view;
    private TextView welcomeText;
    private TextView depositAmount;
    private SeekBar depositSeekBar;

    private AccountViewModel accountViewModel;
    private Account account;
    private List<Transaction> transactions;

    public DepositFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.deposit_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        welcomeText = this.view.findViewById(R.id.deposit_welcome);
        depositAmount = this.view.findViewById(R.id.depositAmount);
        TextView depositText = this.view.findViewById(R.id.deposit_text);
        depositSeekBar = this.view.findViewById(R.id.seekBarDeposit);
        Button deposit = this.view.findViewById(R.id.button_deposit);
        Bundle bundle = getArguments();
        assert bundle != null;
        account = (Account) bundle.getSerializable("account");
        assert account != null;
        this.transactions = getTransactions(account.getId());
        welcomeText.setText(String.format("Deposit To: %s", account.toString()));
        /*Deposit money to an account and update the account with than new deposit transaction*/
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Transaction> newTransactions = deposit(Double.parseDouble(depositAmount.getText().toString()));
                transactions.addAll(newTransactions);
                account.setTransactionList(transactions);
                accountViewModel.updateAccount(account);
                welcomeText.setText(account.toString());
                Snackbar.make(v,depositAmount.getText().toString() + " added to account",
                        Snackbar.LENGTH_SHORT).show();
                depositSeekBar.setProgress(0);
                Bundle bundle = new Bundle();
                Account transferAcc = account;
                bundle.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_deposit_fragment_to_account_fragment, bundle);
            }
        });
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        controller.popBackStack(R.id.account_fragment, false);
                    }
                });

        depositSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int amountToDeposit = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amountToDeposit = progress;
                depositAmount.setText(String.valueOf(amountToDeposit));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                depositAmount.setText(String.valueOf(amountToDeposit));
            }
        });


    }
    /*Deposit money to an account and return all the transactions for this account, also create
    * a transaction from this deposit*/
    private List<Transaction> deposit(Double amount) {
        if (amount > 0) {
            try {
                Transaction transaction = this.account.deposit(amount);
                //this.account.addToTransactionList(transaction);
                accountViewModel.insertTransaction(transaction);
                List<Transaction> newTransactions = getTransactions(this.account.getId());
                account.setTransactionList(newTransactions);
                return newTransactions;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return account.getTransactionList();
    }
    /*Gets the transactions for this account. TODO proper error handling...*/
    private List<Transaction> getTransactions(int id) {
        try {
            this.transactions = accountViewModel.getTransactionsList(id);
            account.setTransactionList(this.transactions);
            return account.getTransactionList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.transactions;
    }
}
