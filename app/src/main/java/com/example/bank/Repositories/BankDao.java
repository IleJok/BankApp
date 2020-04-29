package com.example.bank.Repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bank.Models.Bank;

import java.util.List;
/* A DAO (data access object) validates your SQL at compile-time and associates it with a method.
 * In your Room DAO, you use handy annotations, like @Insert, to represent the most common database
 * operations! Room uses the DAO to create a clean API for your code.
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#4*/
@Dao
public abstract class BankDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Bank bank);

    @Update
    public abstract void updateBanks(Bank... banks);

    @Delete
    public abstract void deleteBanks(Bank... banks);

    @Query("DELETE FROM banks")
    public abstract void deleteAllBanks();

    @Query("SELECT * FROM banks")
    public abstract List<Bank> loadAllBanks();

    @Query("SELECT * FROM banks WHERE id = :id")
    public abstract Bank getBank(int id);

    @Query("SELECT * FROM banks WHERE name =:name")
    public abstract Bank getBankWithName(String name);

}
