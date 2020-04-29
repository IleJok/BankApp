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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.bank.Models.Account;
import com.example.bank.Models.Card;
import com.example.bank.Models.Transaction;
import com.example.bank.R;
import com.example.bank.Repositories.CSVWriter;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * This fragment could be described as the main view of this application. Through this view,
 * customer can see transactions and navigate to different views like transfer money, withdraw,
 * deposit, modify account and so on.
 * Accessed from profile fragment through clicking one the accounts customer has.
 * Layout file account_fragment.xml
 */
public class AccountFragment extends Fragment {
    private View view;
    private Spinner cardSpinner;
    private AccountViewModel accountViewModel;
    private Account account;
    private List<Transaction> transactions;
    private List<Card> cards;

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
        TextView welcomeText = this.view.findViewById(R.id.accountViewText);

        Button addCard = this.view.findViewById(R.id.button_add_card);
        Button editAccount = this.view.findViewById(R.id.button_edit_account);
        Button deposit = this.view.findViewById(R.id.button_deposit);
        Button withdraw = this.view.findViewById(R.id.button_withdraw);
        Button transfer = this.view.findViewById(R.id.button_transfer);
        Button cardDetails = this.view.findViewById(R.id.button_use_card);

        Bundle bundle = getArguments();
        assert bundle != null;
        this.account = (Account) bundle.getSerializable("account");
        assert account != null;
        System.out.println("Account id " + account.getId());
        this.transactions = getTransactions(account.getId());
        this.cards = getCards(account.getId());
        welcomeText.setText(account.toString());

        if (this.cards== null)
            this.cards = new ArrayList<>();
        cardSpinner = this.view.findViewById(R.id.cards_spinner);
        ArrayAdapter<Card> cardArrayAdapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_spinner_item, cards);
        cardArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cardSpinner.setAdapter(cardArrayAdapter);
        /*Notify the adapter which holds the Customers cards for changes*/
        accountViewModel.getCardsForAccount(account.getId()).observe(requireActivity(), new Observer<List<Card>>() {
            @Override
            public void onChanged(@Nullable final List<Card> cardsList) {
                cardArrayAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView recyclerView = this.view.findViewById(R.id.transaction_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        final TransactionListAdapter transactionListAdapter = new TransactionListAdapter(this.transactions);
        recyclerView.setAdapter(transactionListAdapter);
        /*Clicking individual transaction at moment only shows the details on the screen by
        * snackbar which is configured in TransactionListAdapter*/
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
        /*Navigate to AddCardFragment where you can add a card to account*/
        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle4 = new Bundle();
                Account transferAcc = account;
                bundle4.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_account_fragment_to_addCardFragment, bundle4);
            }
        });
        /*Navigate to ModifyAccountFragment where you can modify an account*/
        editAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = new Bundle();
                Account transferAcc = account;
                bundle1.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_accountFragment_to_modifyAccountFragment, bundle1);
            }
        });
        /*Navigate to DepositFragment where you can deposit money to an account*/
        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle2 = new Bundle();
                Account transferAcc = account;
                bundle2.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_account_fragment_to_deposit_fragment, bundle2);
            }
        });
        /*Navigate to WithdrawFragment where you can withdraw money from an account with(out) card*/
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle3 = new Bundle();
                Account transferAcc = account;
                bundle3.putSerializable("account", transferAcc);
                List<Card> transferCards = cards;
                bundle3.putSerializable("cards", (Serializable)transferCards);
                controller.navigate(R.id.action_account_fragment_to_withdraw_fragment, bundle3);
            }
        });
        /*Navigate to TransferFragment where you can transfer money from an account to another
        * account*/
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle5 = new Bundle();
                Account transferAcc = account;
                bundle5.putSerializable("account", transferAcc);
                controller.navigate(R.id.action_account_fragment_to_transfer_fragment, bundle5);
            }
        });
        /*Display card details and modify them*/
        cardDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = (Card) cardSpinner.getSelectedItem();
                Bundle bundle5 = new Bundle();
                Account transferAcc = account;
                bundle5.putSerializable("account", transferAcc);
                bundle5.putSerializable("card", card);
                Snackbar.make(v, card.toString(),
                        Snackbar.LENGTH_SHORT).show();
                controller.navigate(R.id.action_account_fragment_to_card_fragment, bundle5);
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
            e.printStackTrace();
        }
        return this.transactions;
    }

    /*Returns cards for this account. TODO implement with LiveData*/
    public List<Card> getCards(int id) {
        try {
            this.cards = accountViewModel.getCardsForAccountNoLive(id);
            account.setCardList(this.cards);
            return account.getCardList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.cards;
    }

}
