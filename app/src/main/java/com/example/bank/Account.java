package com.example.bank;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "accounts", foreignKeys = { @ForeignKey(entity = Bank.class,
parentColumns = "id", childColumns = "bankId", onDelete = CASCADE), @ForeignKey(
        entity = Customer.class, parentColumns = "id", childColumns = "customerId",
        onDelete = CASCADE
)})
public class Account {

    @PrimaryKey(autoGenerate = true)
    @NonNull
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

    // TODO add List of cards
    // TODO add List of transactions

    Account() {

    }

    Account(int bank, int customer, String aType, String bic, Double bal, Boolean trans, Boolean payments){

        this.bankId = bank;
        this.customerId = customer;
        this.accountType = aType;
        this.bankBIC = bic;
        this.balance = bal;
        this.transfers = trans;
        this.cardPayments = payments;
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
    I'm using the uuid as the main source for comparison
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
