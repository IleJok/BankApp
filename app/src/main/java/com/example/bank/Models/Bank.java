package com.example.bank.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "banks")
public class Bank implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String name;
    private String address;
    private String country;
    private String BIC;
    // Customers in this bank
    @Ignore
    private ArrayList<Customer> customers;
    // Accounts in this bank
    @Ignore
    private ArrayList<Account> accounts;
    @Ignore
    Bank() {

    }

    /*Simple constructor for Bank class. Could use Singleton pattern, but I want to
    * have multiple banks in the future*/
    public Bank(String name, String address, String country, String BIC) {

        this.name = name;
        this.address = address;
        this.country = country;
        this.BIC = BIC;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBIC() {
        return this.BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /* Add customer to arrayList if it is not there already, return true if added
        and return false if not.
         */
/*    public boolean addCustomer(Customer customer) {
        if (!this.customers.contains(customer)) {
            this.customers.add(customer);
            return true;
        } else {
            return false;
        }
    }*/

    /* Add account to arrayList if it is not there already, return true if added
    and return false if not.
     */
/*    public boolean addAccount(Account account) {
        if (!this.accounts.contains(account)) {
            this.accounts.add(account);
            return true;
        } else {
            return false;
        }
    }*/

 /*   *//*Get all the customers in the bank*//*
    public ArrayList<Customer> getCustomers() {
        return this.customers;
    }

    *//*Get all the accounts in the bank*//*
    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }*/
    /*equals function compares two objects if they are identical or not. I use the UUID
    * as "main" source for comparison, because it should be unique*/
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        /*FROM the JAVA docs:
        The java.lang.Class.isAssignableFrom() determines if the class or interface represented by
         this Class object is either the same as, or is a superclass or superinterface of,
         the class or interface represented by the specified Class parameter.*/
        if (!Bank.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final Bank bank = (Bank) obj;
        if (this.id != bank.id) {
            return false;
        } else {
            return true;
        }

    }

    @NonNull
    @Override
    public String toString() { // overriding the toString() method
        return name + " " + address + " " + country + " " + BIC;
    }


}
