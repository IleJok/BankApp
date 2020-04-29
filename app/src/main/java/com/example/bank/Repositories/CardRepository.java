package com.example.bank.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bank.Models.Card;

import java.util.List;
/*A Repository class abstracts access to multiple data sources. The Repository is not part of the
Architecture Components libraries, but is a suggested best practice for code separation and
 architecture. A Repository class provides a clean API for data access to the rest of
 the application. https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7*/
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
    public LiveData<List<Card>> getCardsForAccount(int accountId) {
        return cardDao.getCardsForAccount(accountId);
    }

    public List<Card> getCardsForAccountNoLive(int accountId) {
        return cardDao.getCardsForAccountNoLive(accountId);
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
