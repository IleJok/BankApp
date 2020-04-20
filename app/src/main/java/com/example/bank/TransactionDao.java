package com.example.bank;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Date;
import java.util.List;

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

    @Query("SELECT * FROM transactions WHERE id =:id")
    public abstract Transaction getTransaction(int id);

}
