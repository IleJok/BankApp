package com.example.bank;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import java.io.Serializable;
import java.util.List;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "accounts", foreignKeys = { @ForeignKey(entity = Bank.class,
parentColumns = "id", childColumns = "bankId", onDelete = CASCADE), @ForeignKey(
        entity = Customer.class, parentColumns = "id", childColumns = "customerId",
        onDelete = CASCADE
)})
public class Account implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int bankId; // FK to a bank

    private int customerId; // FK to a customer

    private String accountType; // Let the customer change her/his account type
    private String bankBIC;
    private Double balance;
    // if transfers are allowed or not
    private Boolean transfers;
    // if cardPayments are allowed or not
    private Boolean cardPayments;



    @Ignore
    private List<Transaction> transactionList;

    // TODO add List of cards
    // TODO add List of transactions

    Account() {

    }

    public Account(int bankId, int customerId, String accountType, String bankBIC, Double balance, Boolean transfers, Boolean cardPayments){

        this.bankId = bankId;
        this.customerId = customerId;
        this.accountType = accountType;
        this.bankBIC = bankBIC;
        this.balance = balance;
        this.transfers = transfers;
        this.cardPayments = cardPayments;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Boolean getCardPayments() {
        return cardPayments;
    }

    public void setCardPayments(Boolean cardPayments) {
        this.cardPayments = cardPayments;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getTransfers() {
        return transfers;
    }

    public void setTransfers(Boolean transfers) {
        this.transfers = transfers;
    }

    public String getBankBIC() {
        return bankBIC;
    }

    public void setBankBIC(String bankBIC) {
        this.bankBIC = bankBIC;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    /* Deposit money to account*/
    public void deposit(Double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    /* Withdraw money from account */
    public void  withdraw(Double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        }
    }
    /* Make own comparison method for account,
    I'm using the id as the main source for comparison
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (!Account.class.isAssignableFrom(object.getClass())){
            return false;
        }

        final Account account = (Account) object;
        if (this.id != account.id){
            return false;
        } else {
            return true;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Account number: " + id
                + ", Balance : " + getBalance();
    }
}
