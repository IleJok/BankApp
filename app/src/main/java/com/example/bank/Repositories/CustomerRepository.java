package com.example.bank.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bank.Models.Account;
import com.example.bank.Models.Bank;
import com.example.bank.Models.Customer;

import java.util.List;
import java.util.concurrent.Future;
/*A Repository class abstracts access to multiple data sources. The Repository is not part of the
Architecture Components libraries, but is a suggested best practice for code separation and
 architecture. A Repository class provides a clean API for data access to the rest of
 the application. https://codelabs.developers.google.com/codelabs/android-room-with-a-view/#7*/
public class CustomerRepository {

    private CustomerDao mCustomerDao;
    private LiveData<List<Customer>> mAllCustomers;
    private Application application;
    /* Dependency injection*/
    public CustomerRepository(Application application) {
        BankRoomDatabase db = BankRoomDatabase.getDatabase(application);
        mCustomerDao = db.customerDao();
        mAllCustomers = mCustomerDao.loadAllCustomers();
        this.application = application;
    }

    // Returns all the customers
    public LiveData<List<Customer>> getAllCustomers() { return mAllCustomers;}
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
        int joku = 0;
        joku = (int)mCustomerDao.insert(customer);
        boolean writer;
        customer.setId(joku);
        CSVWriter csvWriter = CSVWriter.getInstance();
        writer = csvWriter.writeCustomer(customer, application);
        System.out.println("Writing to csv succeeded: "+ writer);
        return joku;
    }
    // Delete given customers from the db
    public void delete(Customer... customers) {

        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.deleteCustomers(customers);
        });
    }
    // Update given customers, can be one or many
    public void update(Customer... customers) {
        boolean writer;
        CSVWriter csvWriter = CSVWriter.getInstance();
        writer = csvWriter.writeCustomer(customers[0], application);
        System.out.println("Writing to csv succeeded: "+ writer);
        BankRoomDatabase.databaseWriteExecutor.execute(() -> {
            mCustomerDao.updateCustomers(customers);
        });
    }

}
