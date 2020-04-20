package com.example.bank;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static androidx.room.ForeignKey.CASCADE;

/* This class is named as customer, could be named as user instead also*/
@Entity(tableName = "customers", foreignKeys = @ForeignKey(entity = Bank.class,
parentColumns = "id", childColumns = "bankId", onDelete = CASCADE))
public class Customer implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private int bankId;
    private String name;
    private String address;
    private String password; // TODO implement proper password for user/customer
    private String country;
    private String phone;
    private String email;

    @Ignore
    private List<Account> accounts;

    @Ignore
    Customer() {

    }

    public Customer(int bankId, String name, String address, String country, String phone, String email, String password) {
        this.bankId = bankId;
        this.name = name;
        this.address = address;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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


    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    /* Make own comparison method for customer,
    I'm using the uuid as the main source for comparison
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (!Customer.class.isAssignableFrom(object.getClass())){
            return false;
        }

        final Customer customer = (Customer) object;
        if (this.id != customer.id){
            return false;
        } else {
            return true;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return name + " " + " " + email;
    }
}
