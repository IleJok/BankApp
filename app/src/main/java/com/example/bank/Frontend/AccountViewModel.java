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

    public void addAccount(int bankId, int customerId, String accountType, String bankBIC, Double balance, Boolean transfers, Boolean cardPayments) throws IOException {
        Account account = new Account(bankId, customerId, accountType, bankBIC, balance, transfers, cardPayments);
        accountRepository.insert(account);

    }
    public void insertTransactions(Account account) {
        accountRepository.insertTransactions(account);
    }

    public List<Transaction> getTransactionsList(int id) {
        return accountRepository.getTransactionsList(id);
    }

    public Account getAccountWithTransactions(int id) {
        return accountRepository.getAccountWithTransactions(id);
    }
    public void insertTransaction(Transaction transaction) {
        transactionRepository.insert(transaction);
    }
    public void updateAccount(Account account) {
        accountRepository.update(account);
    }

    public void insertCard(Card card) {cardRepository.insert(card);}

    public LiveData<List<Card>> getCardsForAccount(int accountId) {
        return cardRepository.getCardsForAccount(accountId);
    }

    public List<Card> getCardsForAccountNoLive(int accountId) {
        return cardRepository.getCardsForAccountNoLive(accountId);
    }
    /*Return the latest account for customer*/
    public Account getLatestAccount( int customerId) {
        return accountRepository.getLatestAccount(customerId);
    }

    public void updateCard(Card card) {
        cardRepository.update(card);
    }

}
