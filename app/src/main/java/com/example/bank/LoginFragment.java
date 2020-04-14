package com.example.bank;

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

import com.google.android.material.snackbar.Snackbar;


/* Inspiration and guide for this code is from the docs:
https://developer.android.com/guide/navigation/navigation-conditional
 */
public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;
    private EditText customerNameEdit;
    private EditText passwordEdit;
    private Button loginButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        customerNameEdit = view.findViewById(R.id.user_name_input);
        passwordEdit = view.findViewById(R.id.user_password_input);

        loginButton = view.findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.authenticate(customerNameEdit.getText().toString(), passwordEdit.getText().toString());
            }
        });

        final NavController controller = Navigation.findNavController(view);
        final View root = view;

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        loginViewModel.refuse();
                        controller.popBackStack(R.id.main_fragment, false);
                    }
                });

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
