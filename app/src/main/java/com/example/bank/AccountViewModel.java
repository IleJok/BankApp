package com.example.bank;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository accountRepository;



    public AccountViewModel(Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
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

    public void updateAccount(Account account) {
        accountRepository.update(account);
    }
}
