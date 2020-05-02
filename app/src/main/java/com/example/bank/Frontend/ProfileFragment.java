package com.example.bank.Frontend;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bank.Models.Account;
import com.example.bank.Models.Bank;
import com.example.bank.Models.Customer;
import com.example.bank.R;
import com.example.bank.Repositories.CSVWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static androidx.constraintlayout.widget.Constraints.TAG;


/* Inspiration and guide for this code is from the docs:
https://developer.android.com/guide/navigation/navigation-conditional
ProfileFragment is accessed from the MainFragment, but the user is redirected to LoginFragment
if he/she hasn't logged in. After the customer has authenticated or made new customer account he/she
is redirected back to this view. From this view you can modify your details or add an bank account.
By clicking one of the accounts, customer is transferred to AccountFragment
Layout file profile_fragment.xml
 */
public class ProfileFragment extends Fragment {
    private LoginViewModel loginViewModel;
    private TextView welcomeText;
    private View view;
    private Bank bank;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        welcomeText = this.view.findViewById(R.id.profileText);
        Bundle bundle1 = getArguments();
        String bankName;
        if (bundle1 != null) {
            try {
                bankName = bundle1.getString("bankName");
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        RecyclerView recyclerView = this.view.findViewById(R.id.recyclerview);
        Button button = this.view.findViewById(R.id.account_fab);
        Button profileButton = this.view.findViewById(R.id.edit_details);
        Button logOut = this.view.findViewById(R.id.log_out);

        loginViewModel.authstate.observe(getViewLifecycleOwner(),
                authState -> {
                    switch (authState){
                        case AUTH: // If customer is authenticated show her/his accounts and welcome
                            int id = showWelcome();
                            List<Account> accounts = new ArrayList<>();
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                            final AccountListAdapter adapter = new AccountListAdapter(accounts);
                            loginViewModel.getAccountsList(id).observe(requireActivity(), new Observer<List<Account>>() {
                                @Override
                                public void onChanged(@Nullable final List<Account> accounts1) {
                                    // Update the cached copy of the accounts in the adapter.
                                    adapter.setAccounts(accounts1);
                                }
                            });
                            if (loginViewModel.bank.getId() == loginViewModel.customer.getBankId())
                                System.out.println("ID ja pankki" + loginViewModel.bank.getName()
                                + loginViewModel.bank.getId());
                            else
                                System.out.println("MIKÄÄN EI TÄSMÄÄ!! pankki" + loginViewModel.bank.getName());
                            bank = getCustomersBank(loginViewModel.customer.getBankId());

                            recyclerView.setAdapter(adapter);
                            /*Clicking item(an account) transfers customer to items AccountFragment*/
                            adapter.setOnItemClickListener(new AccountListAdapter.ClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    Log.d(TAG, "onItemClick position: " + position);
                                    Account account = adapter.getItem(position);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("account", account);
                                    controller.navigate(R.id.action_profile_fragment_to_accountFragment, bundle);
                                }

                                @Override
                                public void onItemLongClick(int position, View v) {
                                    Log.d(TAG, "onItemClick position: " + position);
                                    Account account = accounts.get(position);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("account", account);
                                    controller.navigate(R.id.action_profile_fragment_to_accountFragment, bundle);
                                }
                            });
                            /*Lets customer to add bank account in AddAccountFragment*/
                            button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("customerId", loginViewModel.customer.getId());
                                    bundle.putInt("bankId", loginViewModel.customer.getBankId());
                                    bundle.putString("bic", bank.getBIC());
                                    controller.navigate(R.id.action_profile_fragment_to_add_Account_Fragment, bundle);
                                }
                            });
                            /*Modify your details in ModifyAccountFragment*/
                            profileButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("customer", loginViewModel.customer);
                                    controller.navigate(R.id.action_profile_fragment_to_editCustomerFragment, bundle);
                                }
                            });
                            /*Log out redirects to log in screen*/
                            logOut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    loginViewModel.refuse();
                                    controller.navigate(R.id.main_fragment);

                                }
                            });

                            break;
                        case UNAUTH: // unauth redirects to LoginFragment
                            controller.navigate(R.id.login_fragment, bundle1);
                            break;
                    }
                });
    }

    /* Displays welcome text for the customer if successfully logged in. Also write the bank to csv
    * and the customer to csv. Then we have a list of banks which have been used and list of
    * customers that have been using the app.*/
    private int showWelcome() {
        String welcome = getString(R.string.welcome);
        this.welcomeText.setText(String.format("%s %s", welcome, loginViewModel.customer.getName()));
        boolean writer;
        CSVWriter csvWriter = CSVWriter.getInstance();
        writer = csvWriter.writeBank(loginViewModel.bank, requireActivity());
        System.out.println("Writing banks to csv file succeeded: "+ writer);
        writer = csvWriter.writeCustomer(loginViewModel.customer, requireActivity());
        System.out.println("Writing to csv succeeded: "+ writer);
        return loginViewModel.customer.getId();
    }

    /*Returns the bank where customer is customer*/
    private Bank getCustomersBank(int bankId) {
        return loginViewModel.getCustomersBank(bankId);
    }

}
