package com.example.bank.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "cards", foreignKeys = @ForeignKey(entity = Account.class,
parentColumns = "id", childColumns = "accountId", onDelete = CASCADE))
public class Card {
    @PrimaryKey
    @NonNull
    private int id;

    private int accountId; // FK to Account entity

    private String cardType;

    private int cardPin;

    private double withdrawLimit;

    private String countryLimit;

    private double creditLimit;
    @Ignore
    public Card() {

    }

    public Card(int accountId, String cardType, int cardPin, double withdrawLimit,
                String countryLimit, double creditLimit) {
        this.accountId = accountId;
        this.cardType = cardType;
        this.cardPin = cardPin;
        this.withdrawLimit = withdrawLimit;
        this.countryLimit = countryLimit;
        this.creditLimit = creditLimit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }


    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public int getCardPin() {
        return cardPin;
    }

    /*Sets the card pin if it is four digits*/
    public String setCardPin(int cardPin) {
        int length = checkPinLength(cardPin);
        if (length == 4) {
            this.cardPin = cardPin;
            return "Pin ok";
        } else if (length > 4) {
            System.out.println("Pin too long, should be four numbers");
            return "Pin too long, should be four numbers";
        } else {
            System.out.println("Pin too short, should be four numbers");
            return "Pin too short, should be four numbers";
        }

    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public String getCountryLimit() {
        return countryLimit;
    }

    public void setCountryLimit(String countryLimit) {
        this.countryLimit = countryLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    /*Returns the length of our card pin*/
    public int checkPinLength(int cardPin) {
        int length = 0;
        long temp = 1;

        while(temp <= cardPin) {
            length++;
            temp *= 10;
        }

        return length;
    }
}
