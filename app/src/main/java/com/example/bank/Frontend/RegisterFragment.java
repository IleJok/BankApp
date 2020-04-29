package com.example.bank.Frontend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.bank.R;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;




import static com.example.bank.Frontend.RegistrationViewModel.RegistrationState.COLLECT_USER_PASSWORD;

/* Inspiration and guide for this code is from the docs:
https://developer.android.com/guide/navigation/navigation-conditional
In this fragment the user(customer) add her/his details and moves to PasswordFragment for setting
password. RegisterFragment is accessed from ProfileFragment if the user is not logged in
Layout file profile_info_fragment.xml
 */
public class RegisterFragment extends Fragment {
    private RegistrationViewModel registrationViewModel;
    private EditText mEditName, mEditAddress, mEditCountry, mEditPhone, mEditEmail;
    private String bankName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_info_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        registrationViewModel = new ViewModelProvider(
                requireActivity()).get(RegistrationViewModel.class);
        final NavController controller = Navigation.findNavController(view);
        Bundle bundle = getArguments();
        assert bundle != null;
        String bankName =bundle.getString("bankName");

        mEditName = view.findViewById(R.id.edit_Name);
        mEditAddress = view.findViewById(R.id.edit_Address);
        mEditCountry = view.findViewById(R.id.edit_Country);
        mEditPhone = view.findViewById(R.id.edit_Phone);
        mEditEmail = view.findViewById(R.id.edit_Email);
        Button saveCustomer = view.findViewById(R.id.button_next);
        /*Collect the input from the user and pass it to registrationViewModel*/
        saveCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEditName.getText().toString();
                String address = mEditAddress.getText().toString();
                String country = mEditCountry.getText().toString();
                String phone = mEditPhone.getText().toString();
                String email = mEditEmail.getText().toString();
                registrationViewModel.collectProfileData(name, address, country,
                        phone, email);
            }
        });

        /*If we are ready to set the password for the user, then advance to next step*/
        registrationViewModel.getRegistrationState().observe(getViewLifecycleOwner(), state -> {
            if (state == COLLECT_USER_PASSWORD) {
                Bundle bundle1 = new Bundle();
                bundle1.putString("bankName", bankName);
                controller.navigate(R.id.move_to_choose_user_password, bundle1);
            }
        });

        /*On back button press, lets go back to login fragment and cancel the registration process*/
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
