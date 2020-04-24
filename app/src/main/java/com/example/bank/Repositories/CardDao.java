package com.example.bank.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bank.Models.Card;
import com.example.bank.Models.Transaction;

import java.util.List;

@Dao
public abstract class CardDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Card card);

    @Update
    public abstract void updateCards(Card...cards);

    @Delete
    public abstract void deleteCards(Card...cards);

    @Query("DELETE FROM cards")
    public abstract void deleteAllCards();

    @Query("SELECT * FROM cards")
    public abstract List<Card> loadAllCards();

    @Query("SELECT * FROM cards WHERE id =:id")
    public abstract Card getCard(int id);

    /*Gets the cards for given account based on the account id*/
    @Query("SELECT * FROM cards WHERE accountId =:accountId")
    public abstract List<Card> getCardsForAccount(int accountId);

    @Query("SELECT * FROM transactions WHERE cardId =: cardId")
    public abstract LiveData<List<Transaction>> getTransactionsForCard(int cardId);
}
