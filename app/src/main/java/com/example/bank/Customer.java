package com.example.bank;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.UUID;

/* This class is named as customer, could be named as user instead also*/
@Entity(tableName = "customers")
public class Customer {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private String name;
    private String address;
    private String password; // TODO implement proper password for user/customer
    private String country;
    private String phone;
    private String email;

    Customer() {

    }

    Customer(String cName, String cAddress, String cCountry, String cPhone, String cEmail, String cPassword) {

        this.name = cName;
        this.address = cAddress;
        this.country = cCountry;
        this.phone = cPhone;
        this.email = cEmail;
        this.password = cPassword;
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
    /* List accounts owned by customer */
    /*public ArrayList<Account> listAccounts() {

    }*/

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
