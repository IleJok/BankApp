package com.example.bank.Repositories;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.bank.Models.Account;
import com.example.bank.Models.Bank;
import com.example.bank.Models.Card;
import com.example.bank.Models.Customer;
import com.example.bank.Models.Transaction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* Room is a database layer on top of an SQLite database
* We want to use multiple threads to query stuff from the db
* so that our UI doesn't take hits to its' performance
* For the first version of the db we have only one
* entity(class). As we implement more classes, we add them to
* entities and increment the version number
* // IJ 11.4.2020
* // TODO add entities and increment the version number
*     also make sure to add abstract interfaces for the entities
*       check BankDao for reference
* */
@Database(entities = {Bank.class, Customer.class, Account.class, Transaction.class, Card.class},
        version = 12, exportSchema = false)
public abstract class BankRoomDatabase extends RoomDatabase {

    public abstract BankDao bankDao();
    public abstract CustomerDao customerDao();
    public abstract AccountDao accountDao();
    public abstract TransactionDao transactionDao();
    public abstract CardDao cardDao();

    private static volatile BankRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    /* We use a singleton pattern for the getting the database
    to avoid having multiple instances running // IJ 11.4.2020
     */
    static BankRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BankRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BankRoomDatabase.class, "bank_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .allowMainThreadQueries() // This should not be the case TODO async
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                // Add couple banks to the app
                BankDao dao = INSTANCE.bankDao();
                CustomerDao customerDao = INSTANCE.customerDao();
                AccountDao accountDao = INSTANCE.accountDao();
                TransactionDao transactionDao = INSTANCE.transactionDao();
                CardDao cardDao = INSTANCE.cardDao();

                // When the app starts again, it wipes all the banks
                dao.deleteAllBanks();
                customerDao.deleteAllCustomers();
                accountDao.deleteAccounts();
                transactionDao.deleteAllTransactions();
                cardDao.deleteAllCards();

                Bank bank = new Bank("Nordea", "Pankkikatu 1",
                        "Suomi", "NDEAFIHH");
                int id = (int) dao.insert(bank);
                /*Bank bank2 = new Bank(2, "OP", "Teollisuuskatu 1",
                        "Suomi", "OKOYFIHH ");
                dao.insert(bank2);
                Bank bank3 = new Bank( 3, "S-Pankki", "Osuuskuntakatu 1",
                        "Suomi", "SBANFIHH ");
                dao.insert(bank3);*/
                Customer customer = new Customer(id, "Ilkka", "testikatu", "Suomi", "044",
                        "ilkka@testi.com", "ilkka");
                int custId = (int) customerDao.insert(customer);

                Account account = new Account(id, custId, "Current Account", bank.getBIC(), (double) 1000.0, true, true);
                int accountId = (int)  accountDao.insert(account);
                Transaction transaction = new Transaction(accountId, 1000.0, "Deposit",
                        "2020/04/23 12:00:00", "NDEAFIHH", accountId);
                transactionDao.insert(transaction);

            });
        }
    };
}
