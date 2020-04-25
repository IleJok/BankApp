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

    static CSVWriter getInstance() {
        if (csvWriter == null) {
            csvWriter = new CSVWriter();
        }
        return csvWriter;
    }

    boolean writeAccount(Account account) throws IOException {
        boolean fileExists;
        try {
            File file = new File(Environment.getExternalStorageDirectory().getPath() + "/accounts.txt");
            if (file.exists())
                fileExists = true;
            else
                fileExists= false;
                file.createNewFile();

            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter out = new BufferedWriter(fileWriter, Context.MODE_PRIVATE);
            if (!fileExists)
                out.write(account.headersCSV());

            out.write(account.toCSV());
            out.close();
            return true;
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        }
    }

}
