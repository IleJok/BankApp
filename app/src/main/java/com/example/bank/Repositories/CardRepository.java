package com.example.bank.Repositories;

import android.app.Application;

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

    List<Card> getAllCards() {return allCards;}

    Card getCard(int id) {return cardDao.getCard(id);}

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
