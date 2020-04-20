package com.example.bank;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


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
        final NavController controller = Navigation.findNavController(view);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        welcomeText = this.view.findViewById(R.id.profileText);
        RecyclerView recyclerView = this.view.findViewById(R.id.recyclerview);
        FloatingActionButton button = this.view.findViewById(R.id.account_fab);

        loginViewModel.authstate.observe(getViewLifecycleOwner(),
                authState -> {
                    switch (authState){
                        case AUTH: // If customer is authenticated show her/his accounts and welcome
                            int id = showWelcome();
                            List<Account> accounts = getAccounts(id);
                            Bank bank = getCustomersBank(loginViewModel.customer.getBankId());
                            recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
                            final AccountListAdapter adapter = new AccountListAdapter(accounts);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new AccountListAdapter.ClickListener() {
                                @Override
                                public void onItemClick(int position, View v) {
                                    Log.d(TAG, "onItemClick position: " + position);
                                    Account account = adapter.getItem(position);
                                    Bundle bundle = new Bundle();
                                    Account transferAcc = account;
                                    bundle.putSerializable("account", transferAcc);
                                    controller.navigate(R.id.action_profile_fragment_to_accountFragment, bundle);
                                }

                                @Override
                                public void onItemLongClick(int position, View v) {
                                    Log.d(TAG, "onItemClick position: " + position);
                                    Account account = accounts.get(position);
                                    Bundle bundle = new Bundle();
                                    Account transferAcc = account;
                                    bundle.putSerializable("account", transferAcc);
                                    controller.navigate(R.id.action_profile_fragment_to_accountFragment, bundle);
                                }
                            });
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

                            break;
                        case UNAUTH:
                            controller.navigate(R.id.login_fragment);
                            break;
                    }
                });
    }

    /* Displays welcome text for the customer if succesfully logged in*/
    public int showWelcome() {
        String welcome = getString(R.string.welcome);
        this.welcomeText.setText(welcome + " " + loginViewModel.customer.getName());
        return loginViewModel.customer.getId();
    }

    /*Returns all accounts that this customer has based on his id*/
    public List<Account> getAccounts(int customerId) {
        Customer customer = loginViewModel.getCustomersAccounts(customerId);
        return customer.getAccounts();
    }

    public Bank getCustomersBank(int bankId) {
        Bank bank = loginViewModel.getCustomersBank(bankId);
        return bank;
    }

}
