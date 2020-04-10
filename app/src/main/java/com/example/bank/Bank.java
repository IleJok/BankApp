package com.example.bank;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.UUID;

public class Bank {
    private UUID id;
    private String name;
    private String address;
    private String country;
    private String BIC;
    // Customers in this bank
    private ArrayList<Customer> customers;

    /*Simple constructor for Bank class. Could use Singleton pattern, but I want to
    * have multiple banks in the future*/
    Bank(String bankName, String bankAddress, String bankCountry, String bicB) {

        this.id = UUID.randomUUID(); // generate random unique id for the bank
        this.name = bankName;
        this.address = bankAddress;
        this.country = bankCountry;
        this.BIC = bicB;
        this.customers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBIC() {
        return BIC;
    }

    public void setBIC(String BIC) {
        this.BIC = BIC;
    }

    public UUID getId() {
        return id;
    }

    /* Add customer to arrayList if it is not there already, return true if added
    and return false if not.
     */
    public boolean addCustomer(Customer customer) {
        if (!this.customers.contains(customer)) {
            this.customers.add(customer);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

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
