package com.example.bank.Repositories;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.example.bank.Models.Account;
import com.example.bank.Models.Transaction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class CSVWriter {

    private static CSVWriter csvWriter = null;

    private CSVWriter() {

    }

    public static CSVWriter getInstance() {
        if (csvWriter == null) {
            csvWriter = new CSVWriter();
        }
        return csvWriter;
    }

    /*Write customers accounts and changes in csv format to 'accounts.txt'
    * It is required by the assignment to write essential information to file even though
    * database is implemented */
    public boolean writeAccount(List<Account> accounts, Context context) throws IOException {
        String fileName = "accounts.txt";
        if (accounts.size() > 0) {
            try {
                File file = context.getFileStreamPath(fileName);
                if (!file.exists()) {
                    OutputStreamWriter ows = new OutputStreamWriter(
                            context.openFileOutput(fileName, Context.MODE_PRIVATE));
                    ows.write(accounts.get(0).headersCSV());
                    for (Account account: accounts) {
                        ows.write(account.toCSV());
                    }
                    ows.close();
                    return true;
                } else {
                    OutputStreamWriter ows = new OutputStreamWriter(
                            context.openFileOutput(fileName, Context.MODE_APPEND));
                    for (Account account: accounts) {
                        ows.write(account.toCSV());
                    }
                    ows.close();
                    return true;
                }

            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    /*Write accounts transactions in csv format to 'transactions.txt'
     * It is required by the assignment to write essential information to file even though
     * database is implemented */
    public boolean writeTransactions(List<Transaction> transactions, Context context) {
        String fileName = "transactions.txt";
        if (transactions.size() > 0) {
            try {
                File file = context.getFileStreamPath(fileName);
                if (!file.exists()) {
                    OutputStreamWriter ows = new OutputStreamWriter(
                            context.openFileOutput(fileName, Context.MODE_PRIVATE));
                    ows.write(transactions.get(0).headersCSV());
                    for (Transaction transaction: transactions) {
                        ows.write(transaction.toCSV());
                    }
                    ows.close();
                    return true;
                } else {
                    OutputStreamWriter ows = new OutputStreamWriter(
                            context.openFileOutput(fileName, Context.MODE_APPEND));
                    ows.write(transactions.get(0).toCSV());
                    ows.close();
                    return true;
                }

            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

}
