package com.example.bank;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AccountWithTransactions {
    @Embedded public Account account;
    @Relation(
            parentColumn = "id",
            entityColumn = "accountId"
    )
    public List<Transaction> transactions;
}
