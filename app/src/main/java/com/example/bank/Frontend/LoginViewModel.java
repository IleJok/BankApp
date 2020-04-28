package com.example.bank.Frontend;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bank.Models.Account;
import com.example.bank.Models.Bank;
import com.example.bank.Models.Customer;
import com.example.bank.Repositories.BankRepository;
import com.example.bank.Repositories.CustomerRepository;

import java.util.List;

/* Inspiration and guide for this code is from the docs:
https://developer.android.com/guide/navigation/navigation-conditional
 */
public class LoginViewModel extends AndroidViewModel {
    // State to tell us whether the user is authenticated or not
    public enum AuthState {
        UNAUTH, // Init state, because user/customer has to login
        AUTH, // Customer has logged in
        INV_AUTH // Login failed
    }
    private CustomerRepository customerRepository;
    private BankRepository bankRepository;
    final MutableLiveData<AuthState> authstate = new MutableLiveData<>();
    Customer customer;
    List<Bank> banks;
    LiveData<List<Customer>> customers;

     public LoginViewModel(Application application){
         super(application);
         authstate.setValue(AuthState.UNAUTH);
         customerRepository = new CustomerRepository(application);
         bankRepository = new BankRepository(application);
         banks = getBanks();
         customers = customerRepository.getAllCustomers();
     }

    /*Method to authenticate*/
    public void authenticate(String customername, String password) {
        if (checkIfPasswordMatches(customername, password)) {
            authstate.setValue(AuthState.AUTH);

        } else {
            authstate.setValue(AuthState.INV_AUTH);
        }
    }

    /*Refuses user from login by setting AuthState to UNAUTH */
    public void refuse(){
        authstate.setValue(AuthState.UNAUTH);
    }

    /*Checks whether password and customername matches and returns true if
    it does and also sets Customer object
    // TODO implement proper authentication or at least password hashing
     */
    public boolean checkIfPasswordMatches(String customername, String password){
        this.customer = getCustomerWithCred(customername, password);

        if (this.customer != null) {
            System.out.println("true " + customer.toString());
            return true;
        } else {
            System.out.println("false, customer on null");

            return false;
        }
    }
    // Gets the customer from db with name, password, bankId TODO implement proper authentication
    Customer getCustomerWithCred(String name, String password) {
        Customer customer1 = customerRepository.getCustomerWithCred(name, password);
        return customer1;
    }
    // Gets the customer with accounts
    Customer getCustomersAccounts(int id) {
        return customerRepository.getCustomerWithAccounts(id);
    }
    public void update(Customer customer) {customerRepository.update(customer);}
    LiveData<List<Account>> getAccountsList(int customerId) {return customerRepository.getAccountsList(customerId);}
    Bank getCustomersBank(int bankId) {return customerRepository.getCustomersBank(bankId);}

    List<Bank> getBanks() {return bankRepository.getAllBanks();}
}
