package com.example.bank.Frontend;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bank.Models.Bank;
import com.example.bank.Models.Card;
import com.example.bank.R;

import java.util.List;


public class MainFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private Spinner bankSpinner;
    private TextView textView;
    private List<Bank> banks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        banks = loginViewModel.getBanks();
        NavController navController = Navigation.findNavController(view);
        textView = view.findViewById(R.id.select_bank);
        bankSpinner = view.findViewById(R.id.banks_spinner);
        ArrayAdapter<Bank> bankArrayAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, banks);
        bankArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bankSpinner.setAdapter(bankArrayAdapter);

        Button button = view.findViewById(R.id.button_view_profile);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bank bank = (Bank) bankSpinner.getSelectedItem();
                Bundle bundle = new Bundle();
                bundle.putString("bankName", bank.getName());
                navController.navigate(R.id.action_main_fragment_to_profileFragment, bundle);
            }
        });

    }
}
