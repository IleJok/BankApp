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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bank.Models.Account;
import com.example.bank.Models.Card;
import com.example.bank.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;
import java.util.SortedSet;
import java.util.TreeSet;


/**
 * A simple {@link Fragment} subclass.
 */
public class CardFragment extends Fragment {
    private View view;
    private TextView editCard, cardDetails, cardType, countryLimit;
    private Spinner cardTypes, countrySpinner;
    private EditText withdrawLimit, cardPin, creditLimit;
    private CheckBox countries;
    private Boolean limitCountry = false;
    private AccountViewModel accountViewModel;
    Card card;
    Account account;
    public CardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.card_fragment, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        Bundle bundle = getArguments();
        this.card = (Card) bundle.getSerializable("card");
        this.account = (Account) bundle.getSerializable("account");

        editCard = this.view.findViewById(R.id.card_edit);
        cardDetails = this.view.findViewById(R.id.card_details);
        cardType = this.view.findViewById(R.id.card_type);
        countryLimit = this.view.findViewById(R.id.country_limit);
        withdrawLimit =  this.view.findViewById(R.id.card_withdraw_limit);
        cardPin =  this.view.findViewById(R.id.card_pin);
        creditLimit =  this.view.findViewById(R.id.card_credit_limit);
        countries = this.view.findViewById(R.id.countries);

        cardTypes = this.view.findViewById(R.id.card_types_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.card_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardTypes.setAdapter(adapter);

        SortedSet<String> allCountries = getAllCountries();
        countrySpinner = this.view.findViewById(R.id.card_another_spinner);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(requireActivity(),
                android.R.layout.simple_spinner_item, allCountries.toArray(new String[0]));
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);
        countrySpinner.setSelection(countryAdapter.getPosition("Finland"));

        Button saveCard = this.view.findViewById(R.id.button_save_card);

        saveCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCard(v);
                Bundle bundle4 = new Bundle();
                Account transferAcc = account;
                bundle4.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_card_fragment_to_account_fragment, bundle4);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        controller.popBackStack(R.id.account_fragment, false);
                    }
                });

        countries.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (countries.isChecked()) {
                    limitCountry = true;
                }
            }
        });
    }

    /*Gets a list of all countries to be used in country limit selection
    * TODO make utils file and put this in there, is already used in two views*/
    public SortedSet<String> getAllCountries() {
        SortedSet<String> allCountries = new TreeSet<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            if (!TextUtils.isEmpty(locale.getDisplayCountry())) {
                allCountries.add(locale.getDisplayCountry());
            }
        }
        return allCountries;
    }

    public void updateCard(View view) {
        int pinWanted = 0;
        try {
            pinWanted = Integer.parseInt(cardPin.getText().toString());

        } catch (NumberFormatException e) {
            e.printStackTrace();
            Snackbar.make(view,"Pin is incorrect",
                    Snackbar.LENGTH_SHORT).show();
        }
        String ok =  card.setCardPin(pinWanted);
        if (ok.equals("Pin ok")) {
            card.setAccountId(this.account.getId());
            card.setCardType(cardTypes.getSelectedItem().toString());
            if (!TextUtils.isEmpty(creditLimit.getText().toString()) && card.getCardType().equals("Credit card"))
                card.setCreditLimit(Double.parseDouble(creditLimit.getText().toString()));
            else
                card.setCreditLimit(0.0);
            if (!TextUtils.isEmpty(withdrawLimit.getText().toString()))
                card.setWithdrawLimit(Double.parseDouble(withdrawLimit.getText().toString()));
            else
                card.setWithdrawLimit(10000.0);
            if (this.limitCountry)
                card.setCountryLimit(countrySpinner.getSelectedItem().toString());
            accountViewModel.updateCard(card);

        }
    }
}
