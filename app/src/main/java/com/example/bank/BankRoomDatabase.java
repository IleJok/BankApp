package com.example.bank;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executor;
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
@Database(entities = {Bank.class, Customer.class}, version = 2, exportSchema = false)
public abstract class BankRoomDatabase extends RoomDatabase {

    public abstract BankDao bankDao();
    public abstract CustomerDao customerDao();

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
                //CustomerDao customerDao = INSTANCE.customerDao();
                // When the app starts again, it wipes all the banks
                dao.deleteAllBanks();
                //customerDao.deleteAllCustomers();

                Bank bank = new Bank("Nordea", "Pankkikatu 1",
                        "Suomi", "NDEAFIHH");
                dao.insert(bank);
                Bank bank2 = new Bank("OP", "Teollisuuskatu 1",
                        "Suomi", "OKOYFIHH ");
                dao.insert(bank2);
                Bank bank3 = new Bank("S-Pankki", "Osuuskuntakatu 1",
                        "Suomi", "SBANFIHH ");
                dao.insert(bank3);
            });
        }
    };
}
