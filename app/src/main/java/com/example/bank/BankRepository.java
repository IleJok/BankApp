package com.example.bank;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class BankRepository {

    private BankDao mBankDao;
    private LiveData<List<Bank>> mAllBanks;

    /* Dependency injection */
    BankRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        mBankDao = db.bankDao();
        mAllBanks = mBankDao.loadAllBanks();

    }

    LiveData<List<Bank>> getAllBanks() {
        return mAllBanks;
    }

    Bank getBank(int id) {
        return mBankDao.getBank(id);
    }

    void insert(Bank bank) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBankDao.insert(bank);
        });
    }

    void delete(Bank... banks) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBankDao.deleteBanks(banks);
        });
    }

    void update(Bank banks) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBankDao.updateBanks(banks);
        });
    }
}
