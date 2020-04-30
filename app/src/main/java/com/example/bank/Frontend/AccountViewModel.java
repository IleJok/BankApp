package com.example.bank.Frontend;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bank.Models.Account;
import com.example.bank.Models.Card;
import com.example.bank.Models.Transaction;
import com.example.bank.Repositories.AccountRepository;
import com.example.bank.Repositories.CSVWriter;
import com.example.bank.Repositories.CardRepository;
import com.example.bank.Repositories.TransactionRepository;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
/*AccountViewModel is used in many fragments, ViewModels main purpose is to handle changes in data
for the user interface. More information about ViewModels:
https://developer.android.com/topic/libraries/architecture/viewmodel*/
public class AccountViewModel extends AndroidViewModel {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private CardRepository cardRepository;

    public AccountViewModel(Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
        transactionRepository = new TransactionRepository(application);
        cardRepository = new CardRepository(application);
    }
    /*Add new account to for the customer*/
    void addAccount(int bankId, int customerId, String accountType, String bankBIC, Double balance, Boolean transfers, Boolean cardPayments) throws IOException {
        Account account = new Account(bankId, customerId, accountType, bankBIC, balance, transfers, cardPayments);
        accountRepository.insert(account);
    }

    List<Transaction> getTransactionsList(int id) {
        return transactionRepository.getTransactionsList(id);
    }

    Account getAccountWithTransactions(int id) {
        return accountRepository.getAccountWithTransactions(id);
    }
    void insertTransaction(Transaction transaction) {
        transactionRepository.insert(transaction);
    }
    void updateAccount(Account account) {
        accountRepository.update(account);
    }

    void insertCard(Card card) {cardRepository.insert(card);}

    LiveData<List<Card>> getCardsForAccount(int accountId) {
        return cardRepository.getCardsForAccount(accountId);
    }

    List<Card> getCardsForAccountNoLive(int accountId) {
        return cardRepository.getCardsForAccountNoLive(accountId);
    }

    void updateCard(Card card) {
        cardRepository.update(card);
    }

}
