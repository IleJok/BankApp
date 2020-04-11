package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BankListAdapter adapter = new BankListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*Bank bank1 = new Bank("Nordea", "Pankkikatu", "Suomi", "FINOR");
        Customer cust1 = new Customer("kalle", "kallenkatu", "Suomi", "00111", "testi@mail.com");
        boolean added = bank1.addCustomer(cust1);
        Account account = new Account(bank1, cust1, (double) 0, false, false);
        System.out.println(account.toString());
*/
        /*
        Bank bank2 = new Bank("Sampo", "Pankkitie", "Suomi", "FISamp");

        System.out.println("Ensimmäisen pankin tiedot: "+ bank1.toString());
        System.out.println("Toisen pankin tiedot: "+ bank2.toString());
        System.out.println("Onko oliot yhtäläiset (pitäisi olla false): "+ bank1.equals(bank2));
        System.out.println("Onko oliot yhtäläiset (pitäisi olla true): "+ bank1.equals(bank1));
        */
    }
}
