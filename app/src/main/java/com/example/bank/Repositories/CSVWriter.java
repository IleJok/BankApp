package com.example.bank.Repositories;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.example.bank.Models.Account;
import com.example.bank.Models.Bank;
import com.example.bank.Models.Card;
import com.example.bank.Models.Customer;
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

/*Writing important things to file was a requirement for this project, so implemented a csvwriter
* class to meet the requirement. Demonstrating here also the singleton pattern*/
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

    /*Write customers account and changes in csv format to 'accounts.txt'
    * It is required by the assignment to write essential information to file even though
    * database is implemented */
    boolean writeAccount(Account account, Context context) throws IOException {
        String fileName = "accounts.txt";
            try {
                File file = context.getFileStreamPath(fileName);
                if (!file.exists()) {
                    OutputStreamWriter ows = new OutputStreamWriter(
                            context.openFileOutput(fileName, Context.MODE_PRIVATE));
                    ows.write(account.headersCSV());
                    ows.write(account.toCSV());
                    ows.close();
                    return true;
                } else {
                    OutputStreamWriter ows = new OutputStreamWriter(
                            context.openFileOutput(fileName, Context.MODE_APPEND));
                    ows.write(account.toCSV());
                    ows.close();
                    return true;
                }
            } catch (FileNotFoundException | UnsupportedEncodingException e) {
                e.printStackTrace();
                return false;
            }
    }

    /*Write accounts transaction in csv format to 'transactions.txt'
     * It is required by the assignment to write essential information to file even though
     * database is implemented */
    public boolean writeTransaction(Transaction transaction, Context context) {
        String fileName = "transactions.txt";
            try {
                File file = context.getFileStreamPath(fileName);
                if (!file.exists()) {
                    OutputStreamWriter ows = new OutputStreamWriter(
                            context.openFileOutput(fileName, Context.MODE_PRIVATE));
                    ows.write(transaction.headersCSV());
                    ows.write(transaction.toCSV());
                    ows.close();
                    return true;
                } else {
                    OutputStreamWriter ows = new OutputStreamWriter(
                            context.openFileOutput(fileName, Context.MODE_APPEND));
                    ows.write(transaction.toCSV());
                    ows.close();
                    return true;
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
    }
    /*Write customer in csv format to 'customers.txt'
     * It is required by the assignment to write essential information to file even though
     * database is implemented */
    public boolean writeCustomer(Customer customer, Context context) {
        String fileName = "customers.txt";
        try {
            File file = context.getFileStreamPath(fileName);
            if (!file.exists()) {
                OutputStreamWriter ows = new OutputStreamWriter(
                        context.openFileOutput(fileName, Context.MODE_PRIVATE));
                ows.write(customer.headersCSV());
                ows.write(customer.toCSV());
                ows.close();
                return true;
            } else {
                OutputStreamWriter ows = new OutputStreamWriter(
                        context.openFileOutput(fileName, Context.MODE_APPEND));
                ows.write(customer.toCSV());
                ows.close();
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*Write card in csv format to 'cards.txt'
     * It is required by the assignment to write essential information to file even though
     * database is implemented */
    /*Write cards in csv format to 'banks.txt'
     * It is required by the assignment to write essential information to file even though
     * database is implemented */
    boolean writeCard(Card card, Context context) {
        String fileName = "cards.txt";
        try {
            File file = context.getFileStreamPath(fileName);
            if (!file.exists()) {
                OutputStreamWriter ows = new OutputStreamWriter(
                        context.openFileOutput(fileName, Context.MODE_PRIVATE));
                ows.write(card.headersCSV());
                ows.write(card.toCSV());
                ows.close();
                return true;
            } else {
                OutputStreamWriter ows = new OutputStreamWriter(
                        context.openFileOutput(fileName, Context.MODE_APPEND));
                ows.write(card.toCSV());
                ows.close();
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    public boolean writeBank(Bank bank, Context context) {
        String fileName = "banks.txt";
        try {
            File file = context.getFileStreamPath(fileName);
            if (!file.exists()) {
                OutputStreamWriter ows = new OutputStreamWriter(
                        context.openFileOutput(fileName, Context.MODE_PRIVATE));
                ows.write(bank.headersCSV());
                ows.write(bank.toCSV());
                ows.close();
                return true;
            } else {
                OutputStreamWriter ows = new OutputStreamWriter(
                        context.openFileOutput(fileName, Context.MODE_APPEND));
                ows.write(bank.toCSV());
                ows.close();
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
