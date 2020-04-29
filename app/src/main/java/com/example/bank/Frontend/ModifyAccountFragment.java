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

import com.example.bank.Models.Account;
import com.example.bank.R;


/**
 * Fragment to modify customers account. Fragment is accessed from AccountFragment.
 * Layout file is modify_account_fragment.xml
 */
public class ModifyAccountFragment extends Fragment {

    public ModifyAccountFragment() {
        // Required empty public constructor
    }
    private View view;
    private CheckBox transfers, cardPayments;
    private boolean transfersAllowed;
    private boolean cardPaymentsAllowed;
    private AccountViewModel accountViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.modify_account_fragment, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        Bundle bundle = getArguments();
        assert bundle != null;
        Account account = (Account) bundle.getSerializable("account");

        transfers = this.view.findViewById(R.id.transfers);
        cardPayments = this.view.findViewById(R.id.card_payments);
        Button saveButton = this.view.findViewById(R.id.button_save);
        assert account != null;
        transfers.setChecked(account.getTransfers());
        cardPayments.setChecked(account.getCardPayments());
        cardPaymentsAllowed = account.getCardPayments();
        transfersAllowed = account.getTransfers();
        Spinner spinner = (Spinner) this.view.findViewById(R.id.account_types_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireActivity(),
                R.array.account_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(account.getAccountType()));

        transfers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (transfers.isChecked()) {
                    transfersAllowed = true;
                } else {
                    transfersAllowed = false;
                }
            }
        });
        cardPayments.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cardPayments.isChecked()) {
                    cardPaymentsAllowed = true;
                } else {
                    cardPaymentsAllowed = false;
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

        /*Update the account to db*/
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setCardPayments(cardPaymentsAllowed);
                account.setTransfers(transfersAllowed);
                account.setAccountType(spinner.getSelectedItem().toString());
                updateAccount(account);
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("account", account);
                controller.navigate(R.id.account_fragment, bundle1);
            }
        });
    }

    /* updates the modified account to db */
    private void updateAccount(Account account) {
        accountViewModel.updateAccount(account);
    }


}
