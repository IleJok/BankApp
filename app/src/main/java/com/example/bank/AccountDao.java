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
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Account account);

    @Update
    void updateAccounts(Account... accounts);

    @Delete
    void deleteAccounts(Account... accounts);

    @Query("DELETE FROM accounts")
    void deleteAllAccounts();

    @Query("SELECT * FROM accounts")
    LiveData<List<Account>> loadAllAccounts();

    @Query("SELECT * FROM accounts WHERE id =:id")
    Account getAccount(int id);

}
