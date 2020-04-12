package com.example.bank;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CustomerDao {

    // On possible conflict do not Insert customer to db
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Customer customer);
    // Update the customers, can be one or many
    @Update
    void updateCustomers(Customer... customers);
    // Delete the customers, can be one or many
    @Delete
    void deleteCustomers(Customer... customers);
    // Delete all customers
    @Query("DELETE FROM customers")
    void deleteAllCustomers();
    // Get all customers from db
    @Query("SELECT * FROM customers")
    LiveData<List<Customer>> loadAllCustomers();
    // Get customer with primary key
    @Query("SELECT * FROM customers WHERE id = :id")
    Customer getCustomer(int id);
    // Get the customer and her/his accounts
   @Query("SELECT * FROM customers WHERE id = :id")
    LiveData<List<CustomerWithAccounts>> getCustomerWithAccounts(int id);
}