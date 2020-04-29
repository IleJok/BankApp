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
    private int id;

    private String name;
    private String address;
    private String country;
    private String BIC;
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
        return this.id == bank.id;

    }

    @NonNull
    @Override
    public String toString() { // overriding the toString() method
        return name + " " + address + " " + country + " " + BIC;
    }

    /*Returns String which is stored to banks.txt file*/
    public String toCSV() {
        return this.id + ";"  + this.name + ";" + this.address +
                ";" + this.country + ";" + this.BIC + ";"+"\n";
    }

    /*Returns String which is stored to banks.txt as a header*/
    public String headersCSV(){
        return "id;name;address;country;phone;email;BIC;\n";
    }


}
