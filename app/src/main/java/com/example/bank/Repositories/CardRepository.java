package com.example.bank.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bank.Models.Card;

import java.util.List;

public class CardRepository {

    private CardDao cardDao;
    private List<Card> allCards;

    /* Dependency injection / Constructor for Repo */
    public CardRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        cardDao = db.cardDao();
        allCards = cardDao.loadAllCards();
    }
    /*GetAllCards from the db*/
    List<Card> getAllCards() {return allCards;}
    /*Get a card based on its id*/
    Card getCard(int id) {return cardDao.getCard(id);}
    /*Get all the cards for this account*/
    public List<Card> getCardsForAccount(int accountId) {
        return cardDao.getCardsForAccount(accountId);
    }

    public void insert(Card card) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            cardDao.insert(card);
        });
    }

    public void delete(Card... cards) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            cardDao.deleteCards(cards);
        });
    }

    public void update(Card... cards) {
        BankRoomDatabase.databaseWriteExecutor.execute(()-> {
            cardDao.updateCards(cards);
        });
    }

}
