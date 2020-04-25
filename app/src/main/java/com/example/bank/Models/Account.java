package com.example.bank.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    private List<Transaction> transactionList = new ArrayList<>();

    @Ignore
    private List<Card> cardList = new ArrayList<>();

    @Ignore
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

    public void addToBalance(Double amount) {this.balance += amount;}

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
        return this.transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
    public void addToTransactionList(Transaction transaction) {
        if (this.transactionList != null) {
            this.transactionList.add(transaction);
        } else {
            List<Transaction> transactions = new ArrayList<>();
            transactions.add(transaction);
            this.setTransactionList(transactions);
        }
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public void addToCardList(Card card) {
        if (this.cardList != null) {
            this.cardList.add(card);
        } else {
            List<Card> cards = new ArrayList<>();
            cards.add(card);
            this.setCardList(cards);
        }
    }

    /* Deposit money to account*/
    public Transaction deposit(Double amount) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        if (amount > 0) {
            this.balance += amount;
            Transaction transaction = new Transaction(this.id, amount, "Deposit",
                    df.format(date), this.bankBIC, this.id);
            System.out.println("transaction " + transaction.toString());
            //this.addToTransactionList(transaction);
            return transaction;
        } else {
            return null;
        }
    }

    /* Transfer money from account to another account TODO implement transfers for future dates*/
    public Transaction transfer(Double amount, Account account) {
        if (this.getTransfers()) {
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            if (this.balance >= amount) {
                this.balance -= amount;
                Transaction transaction = new Transaction(this.id, amount, "Transfer",
                        df.format(date), this.bankBIC, account.getId());
                account.addToBalance(amount); // Increment the balance of receiving account
                System.out.println("transaction " + transaction.toString());
                //this.addToTransactionList(transaction);
                return transaction;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
    /* Withdraw money from account */
    public Transaction withdraw(Double amount) {
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        if (this.balance >= amount) {
            this.balance -= amount;
            Transaction transaction = new Transaction(this.id, amount * -1, "Withdraw",
                    df.format(date), this.bankBIC, this.id);
            System.out.println("transaction " + transaction.toString());
            return transaction;
        } else {
            return null;
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
