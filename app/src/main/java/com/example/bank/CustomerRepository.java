package com.example.bank;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class CustomerRepository {

    private CustomerDao mCustomerDao;
    private LiveData<List<Customer>> mAllCustomers;

    /* Dependency injection*/
    CustomerRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        mCustomerDao = db.customerDao();
        mAllCustomers = mCustomerDao.loadAllCustomers();
    }

    // Returns all the customers
    LiveData<List<Customer>> getAllCustomers() { return mAllCustomers;}
    // Get all accounts from the customer
    LiveData<List<CustomerWithAccounts>> getCustomerWithAccounts(int id) {
        return mCustomerDao.getCustomerWithAccounts(id);
    }
    // Get customer with the id
    Customer getCustomer(int id) {return mCustomerDao.getCustomer(id);}
    // Insert customer to database
    void insert(Customer customer) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.insert(customer);
        });
    }
    // Delete given customers from the db
    void delete(Customer... customers) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.deleteCustomers(customers);
        });
    }
    // Update given customers, can be one or many
    void update(Customer... customers) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.updateCustomers(customers);
        });
    }
}
