package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        Bank bank1 = new Bank("Nordea", "Pankkikatu", "Suomi", "FINOR");
        Bank bank2 = new Bank("Sampo", "Pankkitie", "Suomi", "FISamp");

        System.out.println("Ensimmäisen pankin tiedot: "+ bank1.toString());
        System.out.println("Toisen pankin tiedot: "+ bank2.toString());
        System.out.println("Onko oliot yhtäläiset (pitäisi olla false): "+ bank1.equals(bank2));
        System.out.println("Onko oliot yhtäläiset (pitäisi olla true): "+ bank1.equals(bank1));
        */
    }
}
