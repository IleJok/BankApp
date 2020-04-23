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
public class DepositFragment extends Fragment {
    View view;
    TextView welcomeText, depositAmount, depositText;
    SeekBar depositSeekBar;

    Button deposit;
    private AccountViewModel accountViewModel;
    Transaction transaction;
    Account account;
    List<Transaction> transactions;

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
        depositText = this.view.findViewById(R.id.deposit_text);
        depositSeekBar = this.view.findViewById(R.id.seekBarDeposit);
        deposit = this.view.findViewById(R.id.button_deposit);
        Bundle bundle = getArguments();
        account = (Account) bundle.getSerializable("account");
        System.out.println("Deposit fragmentin account" + account.toString());
        assert account != null;
        this.transactions = getTransactions(account.getId());
        welcomeText.setText("Deposit To: " + account.toString());
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
