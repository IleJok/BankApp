package com.example.bank.Repositories;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bank.Models.Account;
import com.example.bank.Models.Bank;
import com.example.bank.Models.Customer;

import java.util.List;
/* A DAO (data access object) validates your SQL at compile-time and associates it with a method.
 * In your Room DAO, you use handy annotations, like @Insert, to represent the most common database
 * operations! Room uses the DAO to create a clean API for your code.
 * https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#4*/
@Dao
public abstract class CustomerDao {

    // On possible conflict do not Insert customer to db
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insert(Customer customer);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract void insertAccountList(List<Account> accounts);

    // Update the customers, can be one or many
    @Update
    public abstract void updateCustomers(Customer... customers);
    // Delete the customers, can be one or many
    @Delete
    public abstract void deleteCustomers(Customer... customers);
    // Delete all customers
    @Query("DELETE FROM customers")
    public abstract void deleteAllCustomers();
    // Get all customers from db
    @Query("SELECT * FROM customers")
    public abstract LiveData<List<Customer>> loadAllCustomers();
    // Get customer with primary key
    @Query("SELECT * FROM customers WHERE id = :id LIMIT 1")
    public abstract Customer getCustomer(int id);
    // Get customer with credentials, used for login // TODO implement proper auth
    @Query("SELECT * FROM customers WHERE name =:name AND password=:password")
    public abstract Customer getCustomerWithCred(String name, String password);
    // Get the customer and her/his accounts
    @Query("SELECT * FROM accounts WHERE customerId =:customerId")
     public abstract LiveData<List<Account>> getAccountsList(int customerId);

    @Query("SELECT * FROM banks WHERE id =:bankId")
    public abstract Bank getCustomersBank(int bankId);

    void insertAccounts(Customer customer) {
        List<Account> accounts = customer.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            accounts.get(i).setCustomerId(customer.getId());
        }
        insertAccountList(accounts);
    }

    @Query("SELECT * FROM accounts WHERE customerId =:customerId")
    public abstract List<Account> getAccountsListNotLive(int customerId);

    Customer getCustomerWithAccounts(int id) {
        Customer customer = getCustomer(id);
        List<Account> accounts = getAccountsListNotLive(id);
        customer.setAccounts(accounts);
        return customer;
    }
}
