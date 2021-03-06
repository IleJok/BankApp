package com.example.bank.Frontend;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bank.Models.Customer;
import com.example.bank.Repositories.CustomerRepository;

import java.util.List;
/*Currently not used, could be later implemented for admin page to have live data for customers*/
public class CustomerViewModel extends AndroidViewModel {

    private CustomerRepository customerRepository;

    private LiveData<List<Customer>> allCustomers;

    public CustomerViewModel (Application application) {
        super(application);
        customerRepository = new CustomerRepository(application);
        allCustomers = customerRepository.getAllCustomers();
    }

    // Get all customers
   LiveData<List<Customer>> getAllCustomers() {return allCustomers;}

    // Get all accounts from the user
/*    Customer getCustomerWithAccounts(int id) {
        return customerRepository.getCustomerWithAccounts(id);}*/
    public void insert(Customer customer) {customerRepository.insert(customer);}
    public void update(Customer... customers) {customerRepository.update(customers);}
    public void delete(Customer... customers) {customerRepository.delete(customers);}

}
