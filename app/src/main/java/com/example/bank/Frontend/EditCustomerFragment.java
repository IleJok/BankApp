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
import android.widget.Button;
import android.widget.EditText;

import com.example.bank.Models.Customer;
import com.example.bank.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditCustomerFragment extends Fragment {
    View view;
    private EditText mEditName, mEditAddress, mEditCountry, mEditPhone, mEditEmail, mEditPassword;
    Button saveCustomer;
    private LoginViewModel loginViewModel;
    Customer customer;
    public EditCustomerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.edit_customer_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        Bundle bundle = getArguments();
        this.customer = (Customer) bundle.getSerializable("customer");
        mEditName = this.view.findViewById(R.id.edit_Name);
        mEditName.setText(this.customer.getName());
        mEditAddress = this.view.findViewById(R.id.edit_Address);
        mEditAddress.setText(this.customer.getAddress());
        mEditCountry = this.view.findViewById(R.id.edit_Country);
        mEditCountry.setText(this.customer.getCountry());
        mEditPhone = this.view.findViewById(R.id.edit_Phone);
        mEditPhone.setText(this.customer.getPhone());
        mEditEmail = this.view.findViewById(R.id.edit_Email);
        mEditEmail.setText(this.customer.getEmail());
        mEditPassword = this.view.findViewById(R.id.edit_Password);
        saveCustomer = this.view.findViewById(R.id.button_save);

        saveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
                controller.navigate(R.id.action_edit_customer_fragment_to_profile_fragment);
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        controller.popBackStack(R.id.profile_fragment, false);
                    }
                });

    }

    public void updateAccount() {
        if (!TextUtils.isEmpty(mEditName.getText()))
            this.customer.setName(mEditName.getText().toString());
        if (!TextUtils.isEmpty(mEditAddress.getText()))
            this.customer.setAddress(mEditAddress.getText().toString());
        if (!TextUtils.isEmpty(mEditCountry.getText()))
            this.customer.setCountry(mEditCountry.getText().toString());
        if (!TextUtils.isEmpty(mEditPhone.getText()))
            this.customer.setPhone(mEditPhone.getText().toString());
        if (!TextUtils.isEmpty(mEditEmail.getText()))
            this.customer.setEmail(mEditEmail.getText().toString());
        if (!TextUtils.isEmpty(mEditPassword.getText().toString()))
            this.customer.setPassword(mEditPassword.getText().toString());
        loginViewModel.update(this.customer);
    }
}
