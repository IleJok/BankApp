package com.example.bank;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BankViewModel extends AndroidViewModel {

    private BankRepository mRepository;

    private LiveData<List<Bank>> mAllBanks;

    public BankViewModel (Application application) {
        super(application);
        mRepository = new BankRepository(application);
        mAllBanks = mRepository.getAllBanks();
    }

    LiveData<List<Bank>> getAllBanks() {return mAllBanks;}
    // TODO implement all the crud functions
    public void insert(Bank bank) {mRepository.insert(bank);}

}
