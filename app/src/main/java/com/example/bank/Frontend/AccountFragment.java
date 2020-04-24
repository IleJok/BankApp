package com.example.bank.Frontend;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bank.Models.Account;
import com.example.bank.Models.Card;
import com.example.bank.Models.Transaction;
import com.example.bank.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    View view;
    TextView welcomeText;
    Spinner cardSpinner;
    Button addCard, editAccount, deposit, withdraw;
    double balance = 0.0;
    int value = 0;
    private AccountViewModel accountViewModel;
    Account account;
    List<Transaction> transactions;
    List<Card> cards;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.account_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController controller = Navigation.findNavController(view);
        accountViewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);
        welcomeText = this.view.findViewById(R.id.accountViewText);
        addCard = this.view.findViewById(R.id.button_add_card);
        editAccount = this.view.findViewById(R.id.button_edit_account);
        deposit = this.view.findViewById(R.id.button_deposit);
        withdraw = this.view.findViewById(R.id.button_withdraw);
        Bundle bundle = getArguments();
        this.account = (Account) bundle.getSerializable("account");
        assert account != null;
        System.out.println("Account id " + account.getId());
        this.transactions = getTransactions(account.getId());
        this.cards = getCards(account.getId());
        welcomeText.setText(account.toString());
        balance = account.getBalance();
        value = (int) balance;

        cardSpinner = this.view.findViewById(R.id.cards_spinner);
        ArrayAdapter<Card> cardArrayAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, cards);
        cardArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardSpinner.setAdapter(cardArrayAdapter);

        RecyclerView recyclerView = this.view.findViewById(R.id.transaction_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        final TransactionListAdapter transactionListAdapter = new TransactionListAdapter(this.transactions);
        recyclerView.setAdapter(transactionListAdapter);
        transactionListAdapter.setOnItemClickListener(new TransactionListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.d(TAG, "onItemClick position: " + position);
            }
            @Override
            public void onItemLongClick(int position, View v) {
                Log.d(TAG, "onItemClick position: " + position);
            }
        });

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle4 = new Bundle();
                Account transferAcc = account;
                bundle4.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_account_fragment_to_addCardFragment, bundle4);
            }
        });

        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                Account transferAcc = account;
                bundle1.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_accountFragment_to_modifyAccountFragment, bundle1);
            }
        });

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                Account transferAcc = account;
                bundle2.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_account_fragment_to_deposit_fragment, bundle2);
            }
        });

        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle3 = new Bundle();
                Account transferAcc = account;
                bundle3.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_account_fragment_to_withdraw_fragment, bundle3);
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

    /*Returns transactions for this account. TODO implement with LiveData*/
    public List<Transaction> getTransactions(int id) {
        try {
            this.transactions = accountViewModel.getTransactionsList(id);
            account.setTransactionList(this.transactions);
            return account.getTransactionList();
        } catch (Exception e) {
            System.out.println("Erroro " + e);
        }
        return this.transactions;
    }
    /*Returns cards for this account. TODO implement with LiveData*/
    public List<Card> getCards(int id) {
        try {
            this.cards = accountViewModel.getCardsForAccount(id);
            account.setCardList(this.cards);
            return account.getCardList();
        } catch (Exception e) {
            System.out.println("Erroro " + e);
        }
        return this.cards;
    }

}
