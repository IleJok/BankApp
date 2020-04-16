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
public abstract class AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Account account);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertTransactionList(List<Transaction>transactions);

    @Update
    public abstract void updateAccounts(Account... accounts);

    @Delete
    public abstract void deleteAccounts(Account... accounts);

    @Query("DELETE FROM accounts")
    public abstract void deleteAllAccounts();

    @Query("SELECT * FROM accounts")
    public abstract List<Account> loadAllAccounts();

    @Query("SELECT * FROM accounts WHERE id =:id")
    public abstract Account getAccount(int id);

    @Query("SELECT * FROM transactions WHERE accountId =:accountId")
    public abstract List<Transaction> getTransactionsList(int accountId);

    public void insertTransactions(Account account) {
        List<Transaction> transactions = account.getTransactionList();
        for (int i = 0; i < transactions.size(); i++) {
            transactions.get(i).setAccountId(account.getId());
        }
        insertTransactionList(transactions);
    }

    public Account getAccountWithTransactions(int id) {
        Account account = getAccount(id);
        List<Transaction> transactions = getTransactionsList(id);
        account.setTransactionList(transactions);
        return account;
    }

}
