package com.example.bank.Frontend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.bank.Models.Bank;
import com.example.bank.Models.Customer;
import com.example.bank.Repositories.BankRepository;
import com.example.bank.Repositories.CustomerRepository;

import java.util.List;
/* Inspiration and guide for this code is from the docs:
https://developer.android.com/guide/navigation/navigation-conditional
Main purpose of this ViewModel is to control the registration state and create the user.
TODO implement proper password encryption and authentication, could use jwt token...
 */
public class RegistrationViewModel extends AndroidViewModel {

    Customer customer; // Cache for a consumer, store later to db
    private CustomerRepository customerRepository;
    private BankRepository bankRepository;
    private final MutableLiveData<RegistrationState> registrationState =
            new MutableLiveData<>();

    /*Use state to define next steps, first collect data from user,
    * then collect the password, then if everything is ok,
    * set registration completed*/
    enum RegistrationState {
        COLLECT_PROFILE_DATA,
        COLLECT_USER_PASSWORD,
        REGISTRATION_COMPLETED
    }
    public RegistrationViewModel(@NonNull Application application) {
        super(application);
        registrationState.setValue(RegistrationState.COLLECT_PROFILE_DATA);
        customerRepository = new CustomerRepository(application);
        bankRepository = new BankRepository(application);
        List<Bank> banks = bankRepository.getAllBanks();
    }

    MutableLiveData<RegistrationState> getRegistrationState() {
        return registrationState;
    }

    /*First time user, so collect data to make a customer, and set registration state to next state*/
    void collectProfileData(String name, String address, String country,
                            String phone, String email){
        this.customer = new Customer();
        this.customer.setName(name);
        this.customer.setAddress(address);
        this.customer.setCountry(country);
        this.customer.setPhone(phone);
        this.customer.setEmail(email);
        registrationState.setValue(RegistrationState.COLLECT_USER_PASSWORD);
    }

    /*Store the customer in to db and set registration to complete*/
    void createAccountAndLogin(String password, String password2, String bankName) {
        if (password.equals(password2) && !password.isEmpty()){
            this.customer.setPassword(password2);
            // Insert the customer into db
            Bank bank = null;
            try {
               bank = bankRepository.getBankWithName(bankName);
            } catch (Exception e) {
                e.printStackTrace();
            }

            assert bank != null;
            this.customer.setBankId(bank.getId());
            this.customerRepository.insert(this.customer);
            // Registration is complete
            registrationState.setValue(RegistrationState.REGISTRATION_COMPLETED);

        }
    }

    /*Set registration state to first state and clear the customer*/
    void userCancelledRegistrationProcess() {
        registrationState.setValue(RegistrationState.COLLECT_PROFILE_DATA);
        customer = null;
    }


}
