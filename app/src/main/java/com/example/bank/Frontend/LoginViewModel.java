package com.example.bank.Frontend;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bank.Models.Account;
import com.example.bank.Models.Bank;
import com.example.bank.Models.Customer;
import com.example.bank.Repositories.BankRepository;
import com.example.bank.Repositories.CSVWriter;
import com.example.bank.Repositories.CustomerRepository;

import java.util.List;
import java.util.Objects;

/* Inspiration and guide for this code is from the docs:
https://developer.android.com/guide/navigation/navigation-conditional
Main purpose of this ViewModel is to control the authentication state.
TODO implement proper password encryption and authentication, could use jwt token...
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
    Bank bank;
    private List<Bank> banks;
    private LiveData<List<Customer>> customers;


     public LoginViewModel(Application application){
         super(application);
         authstate.setValue(AuthState.UNAUTH);
         customerRepository = new CustomerRepository(application);
         bankRepository = new BankRepository(application);
         banks = getBanks();
         customers = customerRepository.getAllCustomers();
     }

    /*Method to authenticate*/
    void authenticate(String customername, String password) {
        if (checkIfPasswordMatches(customername, password)) {
            authstate.setValue(AuthState.AUTH);

        } else {
            authstate.setValue(AuthState.INV_AUTH);
        }
    }

    /*Refuses user from login by setting AuthState UNAUTH and move the customer to log in screen */
    void refuse(){
        authstate.setValue(AuthState.UNAUTH);
    }

    /*Checks whether password and customername matches and returns true if
    it does and also sets Customer object
    // TODO implement proper authentication or at least password hashing
     */
    private boolean checkIfPasswordMatches(String customername, String password){
        this.customer = getCustomerWithCred(customername, password);
        if (this.customer != null) {
            this.bank = bankRepository.getBank(customer.getBankId());
        }
        if (this.customer != null && this.bank != null) {
            System.out.println("true " + customer.toString());
            return true;
        } else {
            System.out.println("false, customer on null");

            return false;
        }
    }
    /* Gets the customer from db with name, password, bankId TODO implement proper authentication*/
    private Customer getCustomerWithCred(String name, String password) {
        return customerRepository.getCustomerWithCred(name, password);
    }
    /* Gets the customer with accounts from db*/
    Customer getCustomersAccounts(int id) {
        return customerRepository.getCustomerWithAccounts(id);
    }
    /*Update the customer to db*/
    public void update(Customer customer) {customerRepository.update(customer);}
    /*Get the list of accounts for this customer from db*/
    LiveData<List<Account>> getAccountsList(int customerId)
    {return customerRepository.getAccountsList(customerId);}
    /*Returns the bank from db*/
    Bank getCustomersBank(int bankId) {return bankRepository.getBank(bankId);}
    /*Returns all the banks stored in the db*/
    List<Bank> getBanks() { return bankRepository.getAllBanks();}
}
