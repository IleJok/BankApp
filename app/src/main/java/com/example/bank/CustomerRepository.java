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

    LiveData<List<Customer>> getAllCustomers() { return mAllCustomers;}

    Customer getCustomer(int id) {return mCustomerDao.getCustomer(id);}

    void insert(Customer customer) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.insert(customer);
        });
    }

    void delete(Customer... customers) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.deleteCustomers(customers);
        });
    }


    void update(Customer... customers) {
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.updateCustomers(customers);
        });
    }
}
