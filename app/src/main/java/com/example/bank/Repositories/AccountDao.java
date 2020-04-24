package com.example.bank.Repositories;

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

    @Query("SELECT * FROM accounts")
    public abstract List<Account> loadAllAccounts();

    @Query("SELECT * FROM accounts WHERE id =:id")
    public abstract Account getAccount(int id);

    /* Get all the transactions for this account either sender or receiver */
    @Query("SELECT * FROM transactions WHERE accountId =:accountId OR receivingId =:accountId ORDER BY transactionDate DESC")
    public abstract List<Transaction> getTransactionsList(int accountId) throws Exception;

    /*Gets the cards for given account based on the account id*/
    @Query("SELECT * FROM cards WHERE accountId =:accountId")
    public abstract List<Card> getCardsForAccount(int accountId);

    /*Inserts transactions from account to db*/
    public void insertTransactions(Account account) {
        List<Transaction> transactions = account.getTransactionList();
        insertTransactionList(transactions);
    }

    /*Returns the account with related transactions*/
    public Account getAccountWithTransactions(int id) {
        Account account = getAccount(id);
        try {
            List<Transaction> transactions = getTransactionsList(id);
            account.setTransactionList(transactions);
        } catch (Exception e) {
            System.out.println("EEEERROR" + e);
        }

        return account;
    }

}
