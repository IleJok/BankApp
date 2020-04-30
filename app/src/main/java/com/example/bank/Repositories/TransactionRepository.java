package com.example.bank.Repositories;

import android.app.Application;

import com.example.bank.Models.Transaction;

import java.util.Iterator;
import java.util.List;
/*A Repository class abstracts access to multiple data sources. The Repository is not part of the
Architecture Components libraries, but is a suggested best practice for code separation and
 architecture. A Repository class provides a clean API for data access to the rest of
 the application. https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7*/
public class TransactionRepository {

    private TransactionDao transactionDao;
    private List<Transaction> allTransactions;
    private List<Transaction> transactions;
    private Application application;
    /* Dependency injection / Constructor for Repo */
    public TransactionRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        transactionDao = db.transactionDao();
        allTransactions = transactionDao.loadAllTransactions();
        this.application = application;
    }

    List<Transaction> getAllTransactions() {return allTransactions;}

    Transaction getTransaction(int id) {return transactionDao.getTransaction(id);}

    /*Gets transactions for given account, also returns them in latest first. Also apply some
     * logic to transfer explained in the method*/
    public List<Transaction> getTransactionsList(int id) {

        try {
            this.transactions = transactionDao.getTransactionsList(id);
        } catch (Exception e) {
            e.printStackTrace();
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

    public void insert(Transaction transaction) {
        boolean writer;
        CSVWriter csvWriter = CSVWriter.getInstance();
        writer = csvWriter.writeTransaction(transaction, application);
        System.out.println("Writing transaction to csv succeeded: "+ writer);
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
