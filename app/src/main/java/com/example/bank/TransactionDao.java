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
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Transaction transaction);

    @Update
    void updateTransactions(Transaction... transactions);

    @Delete
    void deleteTransactions(Transaction... transactions);

    @Query("DELETE FROM transactions")
    void deleteAllTransactions();

    @Query("SELECT * FROM transactions")
    LiveData<List<Transaction>> loadAllTransactions();

    @Query("SELECT * FROM transactions WHERE id =:id AND transactionDate BETWEEN :someDate AND :anotherDate")
    LiveData<List<Transaction>> loadTransactionsBetweenDates(int id, String someDate, String anotherDate);

    @Query("SELECT * FROM transactions WHERE id =:id")
    Transaction getTransaction(int id);

}
