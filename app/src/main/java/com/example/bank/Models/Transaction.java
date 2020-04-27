package com.example.bank.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.bank.Models.Account;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transactions", foreignKeys = @ForeignKey(entity = Account.class,
parentColumns = "id", childColumns = "accountId", onDelete = CASCADE))
public class Transaction implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private int accountId; // FK to Account entity

    private int cardId;

    private double amount;

    private String transactionType;

    private String transactionDate;

    private int receivingId; // id for the account which is receiving the transaction

    private String receivingBIC; // BIC for the receiver

    private String bic; // Banks BIC

    @Ignore
    Transaction() {

    }
    /*Constructor without card id */
    @Ignore
    public Transaction(int accountId, double amount, String transactionType, String transactionDate, String bic, int receivingId) {
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.bic = bic;
        this.receivingId = receivingId;
    }
    /*Constructor with card id */
    @Ignore
    public Transaction(int accountId, int cardId, double amount, String transactionType, String transactionDate, String bic, int receivingId) {
        this.accountId = accountId;
        this.cardId = cardId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.bic = bic;
        this.receivingId = receivingId;

    }
    /*Constructor with receivers BIC, but without card id*/
    @Ignore
    public Transaction(int accountId, double amount, String transactionType, String transactionDate, String bic, int receivingId, String receivingBIC) {
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.bic = bic;
        this.receivingId = receivingId;
        this.receivingBIC = receivingBIC;
    }
    /*Constructor with receivers BIC*/
    public Transaction(int accountId, int cardId, double amount, String transactionType, String transactionDate, String bic, int receivingId, String receivingBIC) {
        this.accountId = accountId;
        this.cardId = cardId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.bic = bic;
        this.receivingId = receivingId;
        this.receivingBIC = receivingBIC;
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

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public int getReceivingId() {
        return receivingId;
    }

    public void setReceivingId(int receivingId) {
        this.receivingId = receivingId;
    }

    public String getReceivingBIC() {
        return receivingBIC;
    }

    public void setReceivingBIC(String receivingBIC) {
        this.receivingBIC = receivingBIC;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    @NonNull
    @Override
    public String toString() {
        switch (this.transactionType) {
            case "Deposit":
            case "Withdraw":
                return "Account: "+ this.getAccountId() + "; BIC:"+this.getBic() +"; Date: " + this.getTransactionDate() + "; Type: " + this.getTransactionType()
                        + "; Amount: " + this.getAmount();
            default:
                return "Account: "+ this.getAccountId() + "; BIC:"+this.getBic() +"; Date: " + this.getTransactionDate() + "; Type: " + this.getTransactionType()
                        + "; Amount: " + this.getAmount() + ", Receiver: " + this.getReceivingId() + ", Receivers BIC: " + this.getReceivingBIC();
        }
    }

    public String toCSV() {
        return this.id + ";" +this.accountId + ";" +this.cardId + ";" +this.amount + ";"
                +this.transactionType + ";" +this.transactionDate + ";" +this.bic + ";"
                +this.receivingId +";" +this.receivingBIC + "\n";
    }

    public String headersCSV(){
        return "id;accountId;cardId;amount;transactionType;transactionDate;BIC;receiverId;receiverBIC;\n";
    }
}
