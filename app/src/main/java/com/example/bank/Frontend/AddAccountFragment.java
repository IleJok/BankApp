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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.example.bank.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;


/**
 * A view for account creation accessed from profile fragment
 * add_account_fragment.xml is the layout file
 */
public class AddAccountFragment extends Fragment {

    private View view;
    private boolean transfersAllowed = false;
    private boolean cardPaymentsAllowed = false;
    private CheckBox transfers, cardPayments;
    private AccountViewModel accountViewModel;
    private int customerId, bankId;
    private String bankBIC;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.add_account_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        /* arguments should never be null, because there are placeholder values declared in the
        action in the nav graph
        */
        assert getArguments() != null;
        customerId = getArguments().getInt("customerId");
        bankId = getArguments().getInt("bankId");
        bankBIC = getArguments().getString("bic");
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        /*Account types are redundant at the moment for the application
        * Account types are declared in the strings resource  */
        Spinner spinner = this.view.findViewById(R.id.account_types_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.account_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        transfers = this.view.findViewById(R.id.transfers);
        cardPayments = this.view.findViewById(R.id.card_payments);
        Button saveAccountButton = this.view.findViewById(R.id.button_save_account);

        /*Saves account to database. And if success, navigate to profile fragment*/
        saveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addAccount(bankId, customerId, spinner.getSelectedItem().toString(), bankBIC,
                            0.0, transfersAllowed, cardPaymentsAllowed);
                    Snackbar.make(v, "Account created!",
                            Snackbar.LENGTH_SHORT).show();
                    controller.navigate(R.id.profile_fragment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Snackbar.make(v, "Account was not created!",
                        Snackbar.LENGTH_SHORT).show();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        controller.popBackStack(R.id.profile_fragment, false);
                    }
                });
        /*Checkbox listener, nothing fancy here, if checked true...*/
        transfers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (transfers.isChecked()) {
                    transfersAllowed = true;
                }
            }
        });
        /*Checkbox listener, nothing fancy here, if checked true...*/
        cardPayments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cardPayments.isChecked()) {
                    cardPaymentsAllowed = true;
                }
            }
        });
    }

    /* Creates an account for the customer*/
    public void addAccount(int bankId, int customerId, String accountType, String bankBIC, double balance, boolean transfers, boolean cardPayments) throws IOException {
        accountViewModel.addAccount(bankId, customerId, accountType, bankBIC, balance, transfers, cardPayments);
    }

}
