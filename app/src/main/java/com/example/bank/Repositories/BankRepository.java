package com.example.bank.Repositories;

import android.app.Application;

import com.example.bank.Models.Bank;

import java.util.List;
/*A Repository class abstracts access to multiple data sources. The Repository is not part of the
Architecture Components libraries, but is a suggested best practice for code separation and
 architecture. A Repository class provides a clean API for data access to the rest of
 the application. https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7*/
public class BankRepository {

    private BankDao mBankDao;
    private List<Bank> mAllBanks;

    /* Dependency injection */
    public BankRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        mBankDao = db.bankDao();
        mAllBanks = mBankDao.loadAllBanks();
    }

    public List<Bank> getAllBanks() {
        return mAllBanks;
    }

    public Bank getBank(int id) {
        return mBankDao.getBank(id);
    }

    public Bank getBankWithName(String bankName) {
        return mBankDao.getBankWithName(bankName);
    }

    public void insert(Bank bank) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBankDao.insert(bank);
        });
    }

    public void delete(Bank... banks) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBankDao.deleteBanks(banks);
        });
    }

    public void update(Bank banks) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mBankDao.updateBanks(banks);
        });
    }
}
