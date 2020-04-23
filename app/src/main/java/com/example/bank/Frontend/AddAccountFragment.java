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


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAccountFragment extends Fragment {

    View view;
    boolean transfersAllowed = false;
    boolean cardPaymentsAllowed = false;
    CheckBox transfers, cardPayments;
    private AccountViewModel accountViewModel;
    private Button saveAccountButton;
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
        customerId = getArguments().getInt("customerId");
        bankId = getArguments().getInt("bankId");
        bankBIC = getArguments().getString("bic");
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        Spinner spinner = (Spinner) this.view.findViewById(R.id.account_types_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.account_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        transfers = this.view.findViewById(R.id.transfers);
        cardPayments = this.view.findViewById(R.id.card_payments);
        saveAccountButton = this.view.findViewById(R.id.button_save_account);
        saveAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAccount(bankId, customerId, spinner.getSelectedItem().toString(), bankBIC, 0.0, transfersAllowed, cardPaymentsAllowed);
                Snackbar.make(v, "Account created!",
                        Snackbar.LENGTH_SHORT).show();
                controller.navigate(R.id.profile_fragment);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        controller.popBackStack(R.id.profile_fragment, false);
                    }
                });
        transfers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (transfers.isChecked()) {
                    transfersAllowed = true;
                }
            }
        });
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
    public void addAccount(int bankId, int customerId, String accountType, String bankBIC, double balance, boolean transfers, boolean cardPayments) {
        accountViewModel.addAccount(bankId, customerId, accountType, bankBIC, balance, transfers, cardPayments);
    }

}
