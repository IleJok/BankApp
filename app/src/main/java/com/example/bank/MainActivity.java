package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bank.BankListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BankViewModel mBankViewModel;
    private CustomerViewModel mCustomerViewModel;

    public static final int NEW_BANK_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final CustomerListAdapter adapter = new CustomerListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCustomerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
        mCustomerViewModel.getAllCustomers().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(List<Customer> customers) {
                adapter.setCustomers(customers);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewCustomerActivity.class);
                startActivityForResult(intent, NEW_BANK_ACTIVITY_REQUEST_CODE);
            }
        });
       /* RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BankListAdapter adapter = new BankListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBankViewModel = new ViewModelProvider(this).get(BankViewModel.class);
        mBankViewModel.getAllBanks().observe(this, new Observer<List<Bank>>() {
            @Override
            public void onChanged(List<Bank> banks) {
                adapter.setBanks(banks);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewBankActivity.class);
                startActivityForResult(intent, NEW_BANK_ACTIVITY_REQUEST_CODE);
            }
        });*/
    }

/*    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_BANK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Customer customer = new Customer(data.getStringExtra(NewCustomerActivity.EXTRA_NAME),
                    data.getStringExtra(NewCustomerActivity.EXTRA_ADDRESS),
                    data.getStringExtra(NewCustomerActivity.EXTRA_COUNTRY),
                    data.getStringExtra(NewCustomerActivity.EXTRA_PHONE),
                    data.getStringExtra(NewCustomerActivity.EXTRA_EMAIL),
                    data.getStringExtra(NewCustomerActivity.EXTRA_PASSWORD));
            mCustomerViewModel.insert(customer);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }*/

/*    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_BANK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bank bank = new Bank(data.getStringExtra(NewBankActivity.EXTRA_BANK_NAME),
                    data.getStringExtra(NewBankActivity.EXTRA_BANK_ADDRESS),
                    data.getStringExtra(NewBankActivity.EXTRA_BANK_COUNTRY),
                    data.getStringExtra(NewBankActivity.EXTRA_BANK_BIC));
            mBankViewModel.insert(bank);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }*/
}
