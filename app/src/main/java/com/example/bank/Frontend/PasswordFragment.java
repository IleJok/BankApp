package com.example.bank.Frontend;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.bank.R;
import com.google.android.material.snackbar.Snackbar;

import static com.example.bank.Frontend.RegistrationViewModel.RegistrationState.REGISTRATION_COMPLETED;

/* Inspiration and guide for this code is from the docs:
https://developer.android.com/guide/navigation/navigation-conditional
In this fragment the user(customer) sets a password. This fragment is accessed from RegisterFragment
If the registration goes well, customer is redirected to ProfileFragment
Layout file choose_password_fragment.xml
 */
public class PasswordFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private RegistrationViewModel registrationViewModel;
    private EditText password, password2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.choose_password_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        registrationViewModel = new ViewModelProvider(
                requireActivity()).get(RegistrationViewModel.class);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        final NavController controller = Navigation.findNavController(view);
        Button registerButton = view.findViewById(R.id.register_button);
        password = view.findViewById(R.id.edit_Password);
        password2 = view.findViewById(R.id.edit_Password2);
        Bundle bundle = getArguments();
        assert bundle != null;
        String bankName = bundle.getString("bankName");
        /*Register button to start the registration process, later if everything is ok, move
        * customer to logged in status*/
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(password.getText()) && !TextUtils.isEmpty(password2.getText())) {
                    String pass = password.getText().toString();
                    String pass2 = password2.getText().toString();
                    registrationViewModel.createAccountAndLogin(pass, pass2, bankName);
                } else {
                    Snackbar.make(view,"Fill both passwords",
                            Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        /*if the registration is complete, move customer to profile fragment*/
        registrationViewModel.getRegistrationState().observe(getViewLifecycleOwner(), state -> {
            if (state == REGISTRATION_COMPLETED) {

                loginViewModel.authenticate(registrationViewModel.customer.getName(),
                        registrationViewModel.customer.getPassword());
                controller.popBackStack(R.id.profile_fragment, false);
            }
        });
        /*Cancel the registration when user clicks back button*/
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        registrationViewModel.userCancelledRegistrationProcess();
                        controller.popBackStack(R.id.login_fragment, false);
                    }
                });
    }
}
