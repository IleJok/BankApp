package com.example.bank.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bank.Models.Account;
import com.example.bank.Models.Bank;
import com.example.bank.Models.Customer;

import java.util.List;

public class CustomerRepository {

    private CustomerDao mCustomerDao;
    private List<Customer> mAllCustomers;

    /* Dependency injection*/
    public CustomerRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        mCustomerDao = db.customerDao();
        mAllCustomers = mCustomerDao.loadAllCustomers();
    }

    // Returns all the customers
    public List<Customer> getAllCustomers() { return mAllCustomers;}
    // Get all accounts from the customer
    public Customer getCustomerWithAccounts(int id) {
        return mCustomerDao.getCustomerWithAccounts(id);
    }
    public LiveData<List<Account>> getAccountsList(int customerId){return mCustomerDao.getAccountsList(customerId);}
    // Get customer with the id
    public Customer getCustomer(int id) {return mCustomerDao.getCustomer(id);}
    public Customer getCustomerWithCred(String name, String password) {
        return mCustomerDao.getCustomerWithCred(name, password);}
    // Insert customer to database
    public int insert(Customer customer) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.insert(customer);
        });
        return customer.getId();
    }
    // Delete given customers from the db
    public void delete(Customer... customers) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.deleteCustomers(customers);
        });
    }
    // Update given customers, can be one or many
    public void update(Customer... customers) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.updateCustomers(customers);
        });
    }

    public Bank getCustomersBank(int bankID) {
        return mCustomerDao.getCustomersBank(bankID);
    }
}
