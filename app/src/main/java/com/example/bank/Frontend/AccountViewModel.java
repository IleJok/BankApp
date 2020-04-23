package com.example.bank.Frontend;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.bank.Models.Account;
import com.example.bank.Models.Transaction;
import com.example.bank.Repositories.AccountRepository;
import com.example.bank.Repositories.TransactionRepository;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;


    public AccountViewModel(Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
        transactionRepository = new TransactionRepository(application);
    }

    public void addAccount(int bankId, int customerId, String accountType, String bankBIC, Double balance, Boolean transfers, Boolean cardPayments) {
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
}
