package com.example.bank.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bank.Models.Account;
import com.example.bank.Models.Card;
import com.example.bank.Models.Transaction;

import java.util.List;

/* A DAO (data access object) validates your SQL at compile-time and associates it with a method.
 * In your Room DAO, you use handy annotations, like @Insert, to represent the most common database
 * operations! Room uses the DAO to create a clean API for your code.
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#4*/
@Dao
public abstract class AccountDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Account account);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertTransactionList(List<Transaction> transactions);

    @Update
    public abstract void updateAccounts(Account... accounts);

    @Delete
    public abstract void deleteAccounts(Account... accounts);

    @Query("DELETE FROM accounts")
    public abstract void deleteAllAccounts();

    @Query("SELECT * FROM accounts WHERE customerId=:customerId ORDER BY id DESC LIMIT 1")
    public abstract Account getLatestAccount(int customerId);

    @Query("SELECT * FROM accounts")
    public abstract List<Account> loadAllAccounts();

    @Query("SELECT * FROM accounts WHERE id =:id")
    public abstract Account getAccount(int id);

    /* Get all the transactions for this account either sender or receiver */
    @Query("SELECT * FROM transactions WHERE accountId =:accountId OR receivingId =:accountId ORDER BY transactionDate DESC")
    public abstract List<Transaction> getTransactionsList(int accountId) throws Exception;
    // Get customers accounts
    @Query("SELECT * FROM accounts WHERE customerId =:customerId")
    public abstract LiveData<List<Account>> getAccountsList(int customerId);
    /*Gets the cards for given account based on the account id*/
    @Query("SELECT * FROM cards WHERE accountId =:accountId")
    public abstract List<Card> getCardsForAccount(int accountId);

    /*Inserts transactions from account to db*/
    void insertTransactions(Account account) {
        List<Transaction> transactions = account.getTransactionList();
        insertTransactionList(transactions);
    }

    /*Returns the account with related transactions*/
    Account getAccountWithTransactions(int id) {
        Account account = getAccount(id);
        try {
            List<Transaction> transactions = getTransactionsList(id);
            account.setTransactionList(transactions);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

}
