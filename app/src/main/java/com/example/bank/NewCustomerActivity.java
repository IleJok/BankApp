package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewCustomerActivity extends AppCompatActivity {
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_ADDRESS = "address";
    public static final String EXTRA_COUNTRY = "country";
    public static final String EXTRA_PHONE = "phone";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_PASSWORD = "password";

    private EditText mEditName;
    private EditText mEditAddress;
    private EditText mEditCountry;
    private EditText mEditPhone;
    private EditText mEditEmail;
    private EditText mEditPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
        mEditName = findViewById(R.id.edit_Name);
        mEditAddress = findViewById(R.id.edit_Address);
        mEditCountry = findViewById(R.id.edit_Country);
        mEditPhone = findViewById(R.id.edit_Phone);
        mEditEmail = findViewById(R.id.edit_Email);
        mEditPassword = findViewById(R.id.edit_Password);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditName.getText())
                || TextUtils.isEmpty(mEditAddress.getText())
                || TextUtils.isEmpty(mEditCountry.getText())
                || TextUtils.isEmpty(mEditPhone.getText())
                || TextUtils.isEmpty(mEditEmail.getText())
                || TextUtils.isEmpty(mEditPassword.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String name = mEditName.getText().toString();
                    String address = mEditAddress.getText().toString();
                    String country = mEditCountry.getText().toString();
                    String phone = mEditPhone.getText().toString();
                    String email = mEditEmail.getText().toString();
                    String password = mEditPassword.getText().toString();
                    replyIntent.putExtra(EXTRA_NAME, name);
                    replyIntent.putExtra(EXTRA_ADDRESS, address);
                    replyIntent.putExtra(EXTRA_COUNTRY, country);
                    replyIntent.putExtra(EXTRA_PHONE, phone);
                    replyIntent.putExtra(EXTRA_EMAIL, email);
                    replyIntent.putExtra(EXTRA_PASSWORD, password);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });

    }
}
