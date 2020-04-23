package com.example.bank.Frontend;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.bank.Models.Bank;
import com.example.bank.Repositories.BankRepository;

import java.util.List;

public class BankViewModel extends AndroidViewModel {

    private BankRepository mRepository;

    private List<Bank> mAllBanks;

    public BankViewModel (Application application) {
        super(application);
        mRepository = new BankRepository(application);
        mAllBanks = mRepository.getAllBanks();
    }

    List<Bank> getAllBanks() {return mAllBanks;}
    // TODO implement all the crud functions
    public void insert(Bank bank) {mRepository.insert(bank);}

}
