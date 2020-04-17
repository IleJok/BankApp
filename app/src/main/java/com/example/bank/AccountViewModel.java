package com.example.bank;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository accountRepository;

    Account account;
    public AccountViewModel(Application application) {
        super(application);
        account = null;
        accountRepository = new AccountRepository(application);
    }

    public int addAccount(int bankId, int customerId, String accountType, String bankBIC, Double balance, Boolean transfers, Boolean cardPayments) {
        this.account.setBankId(bankId);
        this.account.setCustomerId(customerId);
        this.account.setAccountType(accountType);
        this.account.setBankBIC(bankBIC);
        this.account.setBalance(balance);
        this.account.setTransfers(transfers);
        this.account.setCardPayments(cardPayments);
        int accountId = accountRepository.insert(this.account);
        return accountId;
    }

}
