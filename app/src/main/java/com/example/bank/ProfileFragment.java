package com.example.bank;

import android.os.Bundle;

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
import android.widget.TextView;


/* Inspiration and guide for this code is from the docs:
https://developer.android.com/guide/navigation/navigation-conditional
 */
public class ProfileFragment extends Fragment {

    private LoginViewModel loginViewModel;
    TextView welcomeText;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        welcomeText = this.view.findViewById(R.id.profileText);
        final NavController controller = Navigation.findNavController(view);
        loginViewModel.authstate.observe(getViewLifecycleOwner(),
                authState -> {
                    switch (authState){
                        case AUTH:
                            showWelcome();
                            break;
                        case UNAUTH:
                            controller.navigate(R.id.login_fragment);
                            break;
                    }
                });
    }

    public void showWelcome() {
        String welcome = getResources().getString(R.string.welcome);
        System.out.println("Welcome on: " + loginViewModel.customer.getName());
        this.welcomeText.setText(welcome + " " + loginViewModel.customer.getName());
    }
}
