package com.example.bank.Repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bank.Models.Transaction;

import java.util.List;
/* A DAO (data access object) validates your SQL at compile-time and associates it with a method.
 * In your Room DAO, you use handy annotations, like @Insert, to represent the most common database
 * operations! Room uses the DAO to create a clean API for your code.
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#4*/
@Dao
public abstract class TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insert(Transaction transaction);

    @Update
    public abstract void updateTransactions(Transaction... transactions);

    @Delete
    public abstract void deleteTransactions(Transaction... transactions);

    @Query("DELETE FROM transactions")
    public abstract void deleteAllTransactions();

    @Query("SELECT * FROM transactions")
    public abstract List<Transaction> loadAllTransactions();

    @Query("SELECT * FROM transactions WHERE id =:id AND transactionDate BETWEEN :someDate AND :anotherDate")
    public abstract List<Transaction> loadTransactionsBetweenDates(int id, String someDate, String anotherDate);

    /* Get all the transactions for this account either sender or receiver */
    @Query("SELECT * FROM transactions WHERE accountId =:accountId OR receivingId =:accountId ORDER BY transactionDate DESC")
    public abstract List<Transaction> getTransactionsList(int accountId) throws Exception;

    @Query("SELECT * FROM transactions WHERE id =:id")
    public abstract Transaction getTransaction(int id);

}
