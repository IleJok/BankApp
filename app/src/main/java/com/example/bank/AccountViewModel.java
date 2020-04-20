package com.example.bank;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

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
}
