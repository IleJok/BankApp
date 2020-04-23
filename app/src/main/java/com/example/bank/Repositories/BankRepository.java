package com.example.bank.Repositories;

import android.app.Application;

import com.example.bank.Models.Bank;

import java.util.List;

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

  /*LiveData<List<BankWithCustomers>> getBankWithCustomers(int id) {
        return mBankDao.getBankWithCustomers(id);
    }
*/

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
