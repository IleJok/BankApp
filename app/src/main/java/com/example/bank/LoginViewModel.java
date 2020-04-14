package com.example.bank;

import android.app.Application;
import android.content.Context;
import android.graphics.Paint;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
    final MutableLiveData<AuthState> authstate = new MutableLiveData<>();

    Customer customer;




     public LoginViewModel(Application application){
         super(application);
         authstate.setValue(AuthState.UNAUTH);
         customer = null;
         customerRepository = new CustomerRepository(application);

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

    Customer getCustomerWithCred(String name, String password) {
        return customerRepository.getCustomerWithCred(name, password);
    }
}
