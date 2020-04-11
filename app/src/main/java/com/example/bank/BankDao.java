package com.example.bank;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BankDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Bank bank);

    @Update
    void updateBanks(Bank... banks);

    @Delete
    void deleteBanks(Bank... banks);

    @Query("DELETE FROM banks")
    void deleteAllBanks();

    @Query("SELECT * FROM banks")
    LiveData<List<Bank>> loadAllBanks();

    @Query("SELECT * FROM banks WHERE id = :id")
    Bank getBank(int id);

}
