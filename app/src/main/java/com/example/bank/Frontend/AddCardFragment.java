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
 * A view for card creation accessed from profile fragment
 * add_card_fragment.xml is the layout file
 */
public class AddCardFragment extends Fragment {
    private View view;
    private EditText withdrawLimit, cardPin, creditLimit;
    private Spinner cardTypeSpinner, countrySpinner;
    private CheckBox country;
    private Boolean limitCountry = false;
    private Account account;
    private AccountViewModel accountViewModel;

    public AddCardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_card_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        Bundle bundle = getArguments();
        assert bundle != null;
        this.account = (Account) bundle.getSerializable("account");
        SortedSet<String> allCountries = getAllCountries();
        TextView welcomeText = this.view.findViewById(R.id.add_card_text);
        TextView cardTypeInfo = this.view.findViewById(R.id.card_type);
        TextView countryLimitInfo = this.view.findViewById(R.id.country_limit);
        withdrawLimit =  this.view.findViewById(R.id.card_withdraw_limit);
        cardPin =  this.view.findViewById(R.id.card_pin);
        creditLimit =  this.view.findViewById(R.id.card_credit_limit);
        country = this.view.findViewById(R.id.countries);

        cardTypeSpinner = this.view.findViewById(R.id.card_types_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.card_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardTypeSpinner.setAdapter(adapter);

        countrySpinner = this.view.findViewById(R.id.card_country_spinner);
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<String>(requireActivity(),
                android.R.layout.simple_spinner_item, allCountries.toArray(new String[0]));
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryAdapter);
        // IF wanted, could take locale from the device, but since emulating...
        countrySpinner.setSelection(countryAdapter.getPosition("Finland"));

        Button saveButton = this.view.findViewById(R.id.button_save_card);
        /*Add card and navigate back to account fragment*/
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCard(v);
                Bundle bundle4 = new Bundle();
                Account transferAcc = account;
                bundle4.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_add_card_fragment_to_account_fragment, bundle4);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        controller.popBackStack(R.id.account_fragment, false);
                    }
                });
        /*Checkbox listener, nothing fancy here, if checked true...*/
        country.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (country.isChecked()) {
                    limitCountry = true;
                }
            }
        });

    }

    /*Add card to an account and to DB TODO make this more readable*/
    private void addCard(View view) {
        Card card = new Card();
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
            card.setCardType(cardTypeSpinner.getSelectedItem().toString());
            if (!TextUtils.isEmpty(creditLimit.getText().toString()) &&
                    card.getCardType().equals("Credit card"))
                card.setCreditLimit(Double.parseDouble(creditLimit.getText().toString()));
            else
                card.setCreditLimit(0.0);
            if (!TextUtils.isEmpty(withdrawLimit.getText().toString()))
                card.setWithdrawLimit(Double.parseDouble(withdrawLimit.getText().toString()));
            else
                card.setWithdrawLimit(10000.0);
            if (this.limitCountry)
                card.setCountryLimit(countrySpinner.getSelectedItem().toString());
            accountViewModel.insertCard(card);
            this.account.addToCardList(card);
        }
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
}
