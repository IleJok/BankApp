package com.example.bank.Repositories;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.bank.Models.Account;
import com.example.bank.Models.Transaction;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/*A Repository class abstracts access to multiple data sources. The Repository is not part of the
Architecture Components libraries, but is a suggested best practice for code separation and
 architecture. A Repository class provides a clean API for data access to the rest of
 the application. https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7*/
public class AccountRepository {

    private AccountDao accountDao;
    private List<Account> allAccounts;
    private Application application;
        /* Dependency injection*/
        public AccountRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        accountDao = db.accountDao();
        allAccounts = accountDao.loadAllAccounts();
        this.application = application;
        }

    List<Account> getAllAccounts() {return allAccounts;}

    // Get all transactions from the account
    public Account getAccountWithTransactions(int id) {
        return accountDao.getAccountWithTransactions(id);
    }
    // Get all accounts for customer
    public LiveData<List<Account>> getAccountsList(int customerId){return accountDao.getAccountsList(customerId);}

    Account getAccount(int id) {return accountDao.getAccount(id);}

    /*Return the latest account for customer*/
    public Account getLatestAccount(int customerId) {
        return accountDao.getLatestAccount(customerId);
    }

    public void insert(Account account) throws IOException {
        boolean writer = false;
        try {
            CSVWriter csvWriter = CSVWriter.getInstance();
            writer = csvWriter.writeAccount(account, application);
        } catch (IndexOutOfBoundsException  | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Writing accounts to csv file succeeded: "+ writer);
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            accountDao.insert(account);
        });
    }

    void delete(Account... accounts) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            accountDao.deleteAccounts(accounts);
        });
    }
    public void insertTransactions(Account account) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            accountDao.insertTransactions(account);
        });
    }
    public void update(Account... accounts) {
        boolean writer = false;
        try {
            CSVWriter csvWriter = CSVWriter.getInstance();
            writer = csvWriter.writeAccount(accounts[0], application);
        } catch (IndexOutOfBoundsException  | IOException e) {
            e.printStackTrace();
        }
        System.out.println("Writing accounts to csv file succeeded: "+ writer);
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            accountDao.updateAccounts(accounts);
        });
    }
}
