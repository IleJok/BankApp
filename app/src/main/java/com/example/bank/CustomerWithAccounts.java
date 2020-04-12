package com.example.bank;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CustomerWithAccounts {
    @Embedded public Customer customer;
    @Relation(
            parentColumn = "id",
            entityColumn = "customerId"
    )
    public List<Account> accounts;
}
