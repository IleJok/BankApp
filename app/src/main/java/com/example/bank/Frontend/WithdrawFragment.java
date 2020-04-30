package com.example.bank.Frontend;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bank.Models.Account;
import com.example.bank.Models.Card;
import com.example.bank.Models.Transaction;
import com.example.bank.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * Fragment to withdraw money from an account. Fragment is accessed from AccountFragment.
 * Layout file is withdraw_fragment.xml
 */
public class WithdrawFragment extends Fragment {
    private View view;
    private TextView welcomeText;
    private TextView withdrawAmount;
    private SeekBar withdrawSeekBar;
    private Spinner cardSpinner;
    private EditText cardPin;
    private AccountViewModel accountViewModel;
    private Transaction transaction;
    private Account account;
    private List<Transaction> transactions;
    private double balance = 0.0;
    private int value = 0;
    private String selectedCountry;
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
        TextView selectCard = this.view.findViewById(R.id.select_card);
        TextView selectCountry = this.view.findViewById(R.id.select_country);
        TextView noCard = this.view.findViewById(R.id.no_card);
        cardPin = this.view.findViewById(R.id.card_pin);

        withdrawAmount = this.view.findViewById(R.id.withdrawAmount);
        TextView withdrawText = this.view.findViewById(R.id.withdraw_text);
        withdrawSeekBar = this.view.findViewById(R.id.seekBarWithdraw);
        Button withdraw = this.view.findViewById(R.id.button_withdraw);
        SortedSet<String> allCountries = getAllCountries();

        Bundle bundle = getArguments();
        assert bundle != null;
        this.account = (Account) bundle.getSerializable("account");
        List<Card> cards = (List<Card>) bundle.getSerializable("cards");
        assert account != null;
        assert cards != null;
        welcomeText.setText(account.toString());
        balance = account.getBalance();
        value = (int) balance;
        withdrawSeekBar.setMax(value);

        Spinner countrySpinner = this.view.findViewById(R.id.card_country_spinner);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(requireActivity(),
                android.R.layout.simple_spinner_item, allCountries.toArray(new String[0]));
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);
        // IF wanted, could take locale from the device, but since emulating...
        countrySpinner.setSelection(countryAdapter.getPosition("Finland"));

        cardSpinner = this.view.findViewById(R.id.cards_spinner);
        ArrayAdapter<Card> cardArrayAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, cards);
        cardArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardSpinner.setAdapter(cardArrayAdapter);

        this.transactions = getTransactions(account.getId());
        /*Withdraw money from the account. If user has no cards, then the withdraw simulates
        * trip to bank and collecting the money from there. Else "use" the selected card and
        * check its limits*/
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO make this mess a couple of function with proper error handling and validation
                Card card = (Card) cardSpinner.getSelectedItem();
                List<Transaction> newTransactions = new ArrayList<>();
                if (card == null) {
                    newTransactions = withdraw(Double.parseDouble(
                            withdrawAmount.getText().toString()));
                } else {
                    int pinToTest = 0;
                    try {
                        pinToTest = Integer.parseInt(cardPin.getText().toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Snackbar.make(v,"Pin is incorrect",
                                Snackbar.LENGTH_SHORT).show();
                    }
                    if (card.validatePin(pinToTest)) {
                        newTransactions = withdrawWithCard(Double.parseDouble(
                                withdrawAmount.getText().toString()), card, selectedCountry);
                    } else {
                        Snackbar.make(v,"Pin is incorrect",
                                Snackbar.LENGTH_SHORT).show();
                    }
                }
                if (newTransactions != null) {
                    if (newTransactions.size() > 0){
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
                } else {
                    Snackbar.make(v,"Withdraw failed",
                            Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        cardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Card card = (Card) cardSpinner.getItemAtPosition(position);
                value += (int)card.getCreditLimit();
                withdrawSeekBar.setMax(value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry = countrySpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    /*Withdraw from account without a card*/
    private List<Transaction> withdraw(Double amount) {
        if (amount > 0) {
            try {
                this.transaction = this.account.withdraw(amount);
                accountViewModel.insertTransaction(this.transaction);
                List<Transaction> newTransactions = accountViewModel.getTransactionsList(this.account.getId());
                account.setTransactionList(newTransactions);
                return newTransactions;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    /*Withdraw from account with a card*/
    private List<Transaction> withdrawWithCard(Double amount, Card card, String selectedCountry) {
        if (amount > 0) {
            try {
                this.transaction = this.account.withdrawWithCard(amount, card, selectedCountry);
                if (this.transaction != null) {
                    accountViewModel.insertTransaction(this.transaction);
                    List<Transaction> newTransactions = accountViewModel.getTransactionsList(this.account.getId());
                    account.setTransactionList(newTransactions);
                    return newTransactions;
                } else {
                    return null;
                }

            } catch (Exception e) {
                e.printStackTrace();
                return account.getTransactionList();
            }
        }
        return account.getTransactionList();
    }
    /*Gets all the transactions for this account. TODO proper error handling*/
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

    /*Gets a list of all countries to be used in country limit selection TODO make utils file*/
    private SortedSet<String> getAllCountries() {
        SortedSet<String> allCountries = new TreeSet<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            if (!TextUtils.isEmpty(locale.getDisplayCountry())) {
                allCountries.add(locale.getDisplayCountry());
            }
        }
        return allCountries;
    }

}
