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
 * Fragment to transfer money to an account. Fragment is accessed from AccountFragment.
 * Layout file is transfer_fragment.xml
 */
public class TransferFragment extends Fragment {
    private View view;
    private TextView transferAmount;
    private EditText accountNumber;
    private SeekBar transferSeekBar;
    private Account account;
    private AccountViewModel accountViewModel;
    private List<Transaction> transactions;
    private double balance = 0.0;
    private int value = 0;

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
        TextView welcomeText = this.view.findViewById(R.id.withdraw_welcome);
        transferAmount = this.view.findViewById(R.id.transferAmount);
        TextView transferText = this.view.findViewById(R.id.transfer_text);
        transferSeekBar = this.view.findViewById(R.id.seekBarTransfer);
        Button transfer = this.view.findViewById(R.id.button_transfer);
        accountNumber = this.view.findViewById(R.id.account_number);
        TextView balanceText = this.view.findViewById(R.id.transfer_balance);
        Bundle bundle = getArguments();
        assert bundle != null;
        this.account = (Account) bundle.getSerializable("account");
        assert account != null;
        balance = this.account.getBalance();
        balanceText.setText(String.format("Your accounts balance: %s", balance));
        value = (int) balance;
        transferSeekBar.setMax(value);
        this.transactions = getTransactions(account.getId());
        /*Make the transfer if the is enough money and legit receiver*/
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO make this onclick handler more robust and divide it into to several functions
                int receiverId = 0;
                double amount = 0;
                try {
                    amount = Double.parseDouble(
                            transferAmount.getText().toString());
                    receiverId = Integer.parseInt(
                            accountNumber.getText().toString());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    Snackbar.make(v,"Give receivers account id/number",
                            Snackbar.LENGTH_SHORT).show();
                }

                if (receiverId > 0 && amount > 0) {
                    List<Transaction> newTransactions = transfer(amount, receiverId
                    , view);
                    if (newTransactions != null) {
                        transactions.addAll(newTransactions);
                        account.setTransactionList(transactions);
                        accountViewModel.updateAccount(account);
                        Snackbar.make(v, transferAmount.getText().toString()
                                        + " transferred from account",
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
                } else {
                    Snackbar.make(v, "Transfer failed",
                            Snackbar.LENGTH_SHORT).show();
                }
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
                transferAmount.setText(String.valueOf(amountToTransfer));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                transferAmount.setText(String.valueOf(amountToTransfer));
            }
        });

    }
    /*Transfers money to given account, adds the new transaction to both receiver and sender*/
    private List<Transaction> transfer(Double amount, int receivingId, View v) {
        Account receiver = accountViewModel.getAccountWithTransactions(receivingId);
        /* Account has to have enough money and receiver cant be null, because then the money
        would disappear*/
        if (amount < this.account.getBalance() && amount > 0 && receiver != null) {
            try {
                Transaction transaction = this.account.transfer(amount, receiver);
                if (transaction != null) {
                    accountViewModel.insertTransaction(transaction);
                    List<Transaction> newTransactions = accountViewModel.getTransactionsList(this.account.getId());
                    account.setTransactionList(newTransactions);
                    receiver.addToTransactionList(transaction);
                    accountViewModel.updateAccount(receiver);
                    return newTransactions;
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }
        if (receiver == null) {
            Snackbar.make(v, "Check receivers account number!",
                    Snackbar.LENGTH_SHORT).show();
        }
        return null;
    }

    /*Returns list of transactions for this account*/
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
