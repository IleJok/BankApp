package com.example.bank.Repositories;

import android.app.Application;
import android.content.Context;

import com.example.bank.Models.Account;
import com.example.bank.Models.Transaction;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

public class AccountRepository {

    private AccountDao accountDao;
    private List<Account> allAccounts;
    private List<Transaction> transactions;
        /* Dependency injection*/
        public AccountRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        accountDao = db.accountDao();
        allAccounts = accountDao.loadAllAccounts();
    }

    List<Account> getAllAccounts() {return allAccounts;}

    // Get all transactions from the account
    public Account getAccountWithTransactions(int id) {
        return accountDao.getAccountWithTransactions(id);
    }



    Account getAccount(int id) {return accountDao.getAccount(id);}

    /*Return the latest account for customer*/
    public Account getLatestAccount(int customerId) {
        return accountDao.getLatestAccount(customerId);
    }
    /*Gets transactions for given account, also returns them in latest first. Also apply some
    * logic to transfer explained in the method*/
    public List<Transaction> getTransactionsList(int id) {

        try {
           this.transactions = accountDao.getTransactionsList(id);
        } catch (Exception e) {
            System.out.println("ERRORRRI " + e.toString());
        }
        /*Iterate through the list of transactions, and if the account is not the receiver
        *of the transaction in transfer, make the amount negative */
        Iterator<Transaction> iterator = this.transactions.iterator();
        while (iterator.hasNext()) {
            Transaction transaction = iterator.next();
            if (transaction.getTransactionType().equals("Transfer")) {
                if (transaction.getReceivingId() != id) {
                    transaction.setAmount(transaction.getAmount()*-1);
                }
            }
        }

        return this.transactions;
    }

    public void insert(Account account) throws IOException {
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
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            accountDao.updateAccounts(accounts);
        });
    }
}
