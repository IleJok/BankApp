package com.example.bank.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "cards", foreignKeys = @ForeignKey(entity = Account.class,
parentColumns = "id", childColumns = "accountId", onDelete = CASCADE))
public class Card implements Serializable {
    @PrimaryKey(autoGenerate = true)
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

    /*Checks if the card is credit card and max credit limit is 10000 */
    public void setCreditLimit(double creditLimit) {
        if (this.cardType.equals("Credit card")) {
            if (creditLimit >= 10000.0) {
                this.creditLimit = 10000.0;
            } else if (creditLimit > 0) {
                this.creditLimit = creditLimit;
            } else {
                this.creditLimit = 0.0;
            }
        } else {
            this.creditLimit = 0;
        }

    }

    /*Validate the given pin*/
    public boolean validatePin(int pin) {
        return this.cardPin == pin;
    }

    @NotNull
    @Override
    public String toString() {
        if (this.cardType.equals("Credit card"))
            return "Card: " + this.id + ", Card type: " + this.cardType + ", Credit limit: " + this.creditLimit;
        else
            return "Card: " + this.id + ", Card type: " + this.cardType;
    }
    /*Returns String which is stored to cards.txt file*/
    public String toCSV() {
        return this.id + ";" + this.accountId + ";" + this.cardType + ";" + this.cardPin +
                ";" + this.withdrawLimit +";" + this.countryLimit + ";" + this.creditLimit + ";"+ "\n";
    }
    /*Returns String which is stored to cards.txt as a header*/
    public String headersCSV(){
        return "id;accountId;cardType;cardPin;withdrawLimit;countryLimit;creditLimit;\n";
    }

    /*Returns the length of our card pin*/
    private int checkPinLength(int cardPin) {
        int length = 0;
        long temp = 1;

        while(temp <= cardPin) {
            length++;
            temp *= 10;
        }

        return length;
    }
}
