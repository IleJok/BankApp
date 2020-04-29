package com.example.bank.Repositories;

import android.app.Application;

import com.example.bank.Models.Transaction;

import java.util.List;
/*A Repository class abstracts access to multiple data sources. The Repository is not part of the
Architecture Components libraries, but is a suggested best practice for code separation and
 architecture. A Repository class provides a clean API for data access to the rest of
 the application. https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7*/
public class TransactionRepository {

    private TransactionDao transactionDao;
    private List<Transaction> allTransactions;

    /* Dependency injection / Constructor for Repo */
    public TransactionRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
        allTransactions = transactionDao.loadAllTransactions();
    }

    List<Transaction> getAllTransactions() {return allTransactions;}

    Transaction getTransaction(int id) {return transactionDao.getTransaction(id);}

    public void insert(Transaction transaction) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            transactionDao.insert(transaction);
        });
    }

    void delete(Transaction... transactions) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            transactionDao.deleteTransactions(transactions);
        });
    }

    void update(Transaction... transactions) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            transactionDao.updateTransactions(transactions);
        });
    }
}
