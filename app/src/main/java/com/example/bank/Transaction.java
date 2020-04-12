package com.example.bank;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "transactions", foreignKeys = @ForeignKey(entity = Account.class,
parentColumns = "id", childColumns = "accountId", onDelete = CASCADE))
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private int accountId;

    private double amount;

    private String transactionType;

    private Date transactionDate;

    private String bic;

    Transaction() {

    }

    public Transaction(int accountId, double amount, String transactionType, Date transactionDate, String bic) {
        this.accountId = accountId;
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = transactionDate;
        this.bic = bic;
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

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
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
        return "Account: "+ this.getAccountId() + "; BIC:"+this.getBic() +"; Date: " + this.getTransactionDate() + "; Type: " + this.getTransactionType()
                + "; Amount: " + this.getAmount();
    }
}
