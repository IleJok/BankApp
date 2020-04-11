package com.example.bank;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {

    private CustomerRepository customerRepository;

    private LiveData<List<Customer>> allCustomers;

    public CustomerViewModel (Application application) {
        super(application);
        customerRepository = new CustomerRepository(application);
        allCustomers = customerRepository.getAllCustomers();
    }

    LiveData<List<Customer>> getAllCustomers() {return allCustomers;}

    public void insert(Customer customer) {customerRepository.insert(customer);}
    public void update(Customer... customers) {customerRepository.update(customers);}
    public void delete(Customer... customers) {customerRepository.delete(customers);}

}
