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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        System.out.println("TULLAAANKO");
       //welcomeText = view.findViewById(R.id.welcome_text);
        
        final NavController controller = Navigation.findNavController(view);
        loginViewModel.authstate.observe(getViewLifecycleOwner(),
                authState -> {
                    switch (authState){
                        case AUTH:
                            //showWelcome();
                            System.out.println("Herran haltuun!!");
                            break;
                        case UNAUTH:
                            controller.navigate(R.id.login_fragment);
                            break;
                    }
                });
    }

/*    private void showWelcome() {
        welcomeText.setText(R.string.welcome);
    }*/
}
