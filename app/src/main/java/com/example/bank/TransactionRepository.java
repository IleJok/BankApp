package com.example.bank;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionRepository {

    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> allTransactions;

    /* Dependency injection */
    TransactionRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
        allTransactions = transactionDao.loadAllTransactions();
    }

    LiveData<List<Transaction>> getAllTransactions() {return allTransactions;}

    Transaction getTransaction(int id) {return transactionDao.getTransaction(id);}

    void insert(Transaction transaction) {
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
