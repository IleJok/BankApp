package com.example.bank;

import androidx.annotation.NonNull;

import java.util.UUID;

public class Account {

    Bank bank;
    Customer customer;
    UUID accountNumber;
    Double balance;
    // if transfers are allowed or not
    Boolean transfers;
    // if cardPayments are allowed or not
    Boolean cardPayments;

    // TODO add List of cards
    // TODO add List of transactions

    Account(Bank bank1, Customer owner, Double bal, Boolean trans, Boolean payments){
        this.accountNumber = UUID.randomUUID();
        this.bank = bank1;
        this.customer = owner;
        this.balance = bal;
        this.transfers = trans;
        this.cardPayments = payments;
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
    /* Return the amount of money currently in the account*/
    public Double checkBalance() {
        return this.balance;
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
        if (this.accountNumber != account.accountNumber){
            return false;
        } else {
            return true;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "Account number: " + accountNumber
                + ", Owner: " + customer.getName() + ", Bank: " + bank.getName();
    }
}
