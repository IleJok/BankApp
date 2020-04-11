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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Customer customer);

    @Update
    void updateCustomers(Customer... customers);

    @Delete
    void deleteCustomers(Customer... customers);

    @Query("DELETE FROM customers")
    void deleteAllCustomers();

    @Query("SELECT * FROM customers")
    LiveData<List<Customer>> loadAllCustomers();

    @Query("SELECT * FROM customers WHERE id = :id")
    Customer getCustomer(int id);

}
