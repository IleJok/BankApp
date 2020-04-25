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
import android.widget.EditText;
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
public class TransferFragment extends Fragment {
    View view;
    TextView welcomeText, balanceText, transferText, transferAmount;
    EditText accountNumber;
    SeekBar transferSeekBar;
    Account account;
    Button transfer;
    private AccountViewModel accountViewModel;
    List<Transaction> transactions;
    Transaction transaction;
    double balance = 0.0;
    int value = 0;

    public TransferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.transfer_fragment, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        welcomeText = this.view.findViewById(R.id.withdraw_welcome);
        transferAmount = this.view.findViewById(R.id.transferAmount);
        transferText = this.view.findViewById(R.id.transfer_text);
        transferSeekBar = this.view.findViewById(R.id.seekBarTransfer);
        transfer = this.view.findViewById(R.id.button_transfer);
        accountNumber = this.view.findViewById(R.id.account_number);
        balanceText = this.view.findViewById(R.id.transfer_balance);
        Bundle bundle = getArguments();
        this.account = (Account) bundle.getSerializable("account");
        assert account != null;
        balance = this.account.getBalance();
        balanceText.setText(String.format("Your accounts balance: %s", balance));
        value = (int) balance;
        transferSeekBar.setMax(value);
        this.transactions = getTransactions(account.getId());

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Transaction> newTransactions = transfer(Double.parseDouble(
                        transferAmount.getText().toString()), Integer.parseInt(
                                accountNumber.getText().toString()
                ));
                transactions.addAll(newTransactions);
                account.setTransactionList(transactions);
                accountViewModel.updateAccount(account);
                Snackbar.make(v,transferAmount.getText().toString() + "transferred from account",
                        Snackbar.LENGTH_SHORT).show();
                balance = account.getBalance();
                value = (int) balance;
                transferSeekBar.setMax(value);
                transferSeekBar.setProgress(0);
                Bundle bundle = new Bundle();
                Account transferAcc = account;
                bundle.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_transfer_fragment_to_account_fragment, bundle);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        controller.popBackStack(R.id.account_fragment, false);
                    }
                });

        transferSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int amountToTransfer = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                amountToTransfer = progress;
                transferAmount.setText(Integer.toString(amountToTransfer));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                transferAmount.setText(Integer.toString(amountToTransfer));
            }
        });

    }

    public List<Transaction> transfer(Double amount, int receivingId) {
        System.out.println("Deposit account" + account.toString());
        Account receiver = accountViewModel.getAccountWithTransactions(receivingId);
        if (amount < this.account.getBalance()) {
            try {
                this.transaction = this.account.transfer(amount, receiver);
                accountViewModel.insertTransaction(this.transaction);
                List<Transaction> newTransactions = accountViewModel.getTransactionsList(this.account.getId());
                account.setTransactionList(newTransactions);
                receiver.addToTransactionList(transaction);
                accountViewModel.updateAccount(receiver);
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
