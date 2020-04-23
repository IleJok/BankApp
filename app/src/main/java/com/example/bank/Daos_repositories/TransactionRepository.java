package com.example.bank.Daos_repositories;

import android.app.Application;

import com.example.bank.Models.Transaction;

import java.util.List;

public class TransactionRepository {

    private TransactionDao transactionDao;
    private List<Transaction> allTransactions;

    /* Dependency injection */
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
