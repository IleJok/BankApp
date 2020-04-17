package com.example.bank;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository accountRepository;
    private BankRepository bankRepository;

    Account account;
    Bank bank;
    public AccountViewModel(Application application) {
        super(application);
        account = null;
        bank = null;
        accountRepository = new AccountRepository(application);
        bankRepository = new BankRepository(application);
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

    public void getBankBIC(int bankId) {

        System.out.println("Pankin id on tämä" + bankId);
        this.bank = bankRepository.getBank(bankId);
        System.out.println("onko meillä pankkia: " +bank.toString());

    }

}
