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
/* A DAO (data access object) validates your SQL at compile-time and associates it with a method.
 * In your Room DAO, you use handy annotations, like @Insert, to represent the most common database
 * operations! Room uses the DAO to create a clean API for your code.
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#4*/
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
    public abstract LiveData<List<Card>> getCardsForAccount(int accountId);

    /*Gets the cards for given account based on the account id*/
    @Query("SELECT * FROM cards WHERE accountId =:accountId")
    public abstract List<Card> getCardsForAccountNoLive(int accountId);

    @Query("SELECT * FROM transactions WHERE cardId =:cardId")
    public abstract LiveData<List<Transaction>> getTransactionsForCard(int cardId);
}
