package com.example.bank.Frontend;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.bank.Models.Bank;
import com.example.bank.R;
import com.google.android.material.snackbar.Snackbar;


/* Inspiration and guide for this code is from the docs:
https://developer.android.com/guide/navigation/navigation-conditional
In this fragment the user(customer) logs in with existing credentials or moves to registration.
LoginFragment is accessed from ProfileFragment if the user is not logged in
Layout file login_fragment.xml
 */
public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private EditText customerNameEdit;
    private EditText passwordEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        final View root = view;
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        String bankName = "";
        try {
            Bundle bundle = getArguments();
            bankName = bundle.getString("bankName");
        } catch (Exception e) {
            e.printStackTrace();
        }

        customerNameEdit = view.findViewById(R.id.user_name_input);
        passwordEdit = view.findViewById(R.id.user_password_input);

        Button registerButton = view.findViewById(R.id.register_button);
        String finalBankName = bankName;
        /*Move to RegisterFragment for adding new customer*/
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("bankName" , finalBankName);
                controller.navigate(R.id.action_login_fragment_to_register_fragment, bundle1);
            }
        });

        Button loginButton = view.findViewById(R.id.login_button);
        /*Log in. If authstate is changed to AUTH, then customer is transferred to ProfileFragment*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.authenticate(customerNameEdit.getText().toString(),
                        passwordEdit.getText().toString());
            }
        });


        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        loginViewModel.refuse();
                        controller.popBackStack(R.id.main_fragment, false);
                    }
                });
        /*If the user is authenticated, move back to ProfileFragment, else display the wrong
        * credentials message*/
        loginViewModel.authstate.observe(getViewLifecycleOwner(),
                new Observer<LoginViewModel.AuthState>() {
                    @Override
                    public void onChanged(LoginViewModel.AuthState authState) {
                        switch (authState) {
                            case AUTH:
                                controller.popBackStack();
                                break;
                            case INV_AUTH:
                                Snackbar.make(root, R.string.invalid_credentials,
                                        Snackbar.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }
}
