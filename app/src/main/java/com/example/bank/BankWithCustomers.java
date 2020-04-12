package com.example.bank;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class BankWithCustomers {
    @Embedded public Bank bank;
    @Relation(
            parentColumn = "id",
            entityColumn = "bankId"
    )
    public List<Customer> customers;
}

