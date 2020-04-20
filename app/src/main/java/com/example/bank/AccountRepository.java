package com.example.bank;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountRepository {

    private AccountDao accountDao;
    private List<Account> allAccounts;
    private List<Transaction> transactions;

        /* Dependency injection*/
    AccountRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        accountDao = db.accountDao();
        allAccounts = accountDao.loadAllAccounts();
    }

    List<Account> getAllAccounts() {return allAccounts;}

    // Get all transactions from the account
    Account getAccountWithTransactions(int id) {
        return accountDao.getAccountWithTransactions(id);
    }

    Account getAccount(int id) {return accountDao.getAccount(id);}
    List<Transaction> getTransactionsList(int id) {

        try {
           this.transactions = accountDao.getTransactionsList(id);
        } catch (Exception e) {
            System.out.println("ERRORRRI " + e.toString());
        }
        return this.transactions;
    }
    void insert(Account account) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            accountDao.insert(account);
        });

    }

    void delete(Account... accounts) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            accountDao.deleteAccounts(accounts);
        });
    }
    void insertTransactions(Account account) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            accountDao.insertTransactions(account);
        });
    }


    void update(Account... accounts) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            accountDao.updateAccounts(accounts);
        });
    }
}
