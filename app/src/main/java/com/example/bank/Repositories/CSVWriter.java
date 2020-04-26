package com.example.bank.Repositories;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.example.bank.Models.Account;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

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

    public boolean writeAccount(Account account, Context context) throws IOException {
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

}
