package com.example.bank.Daos_repositories;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bank.Models.Bank;

import java.util.List;

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

    /*@Transaction
    @Query("SELECT * FROM banks WHERE id = :id")
    LiveData<List<BankWithCustomers>> getBankWithCustomers(int id);

    @Transaction
    @Query("SELECT * FROM banks WHERE id = :id")
    LiveData<List<BankWithAccounts>> getBankWithAccounts(int id);*/

}
