package com.example.bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewBankActivity extends AppCompatActivity {
    public static final String EXTRA_BANK_NAME = "name";
    public static final String EXTRA_BANK_ADDRESS = "address";
    public static final String EXTRA_BANK_COUNTRY = "country";
    public static final String EXTRA_BANK_BIC = "bic";

    private EditText mEditBankName;
    private EditText mEditBankAddress;
    private EditText mEditBankCountry;
    private EditText mEditBankBIC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_bank);
        mEditBankName = findViewById(R.id.edit_bankName);
        mEditBankAddress = findViewById(R.id.edit_bankAddress);
        mEditBankCountry = findViewById(R.id.edit_bankCountry);
        mEditBankBIC = findViewById(R.id.edit_bankBIC);

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                if (TextUtils.isEmpty(mEditBankName.getText() )
                        || TextUtils.isEmpty(mEditBankAddress.getText())
                        || TextUtils.isEmpty(mEditBankCountry.getText())
                        || TextUtils.isEmpty(mEditBankBIC.getText())){
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String bankName = mEditBankName.getText().toString();
                    String bankAddress = mEditBankAddress.getText().toString();
                    String bankCountry = mEditBankCountry.getText().toString();
                    String bankBic = mEditBankBIC.getText().toString();
                    replyIntent.putExtra(EXTRA_BANK_NAME, bankName);
                    replyIntent.putExtra(EXTRA_BANK_ADDRESS, bankAddress);
                    replyIntent.putExtra(EXTRA_BANK_COUNTRY, bankCountry);
                    replyIntent.putExtra(EXTRA_BANK_BIC, bankBic);
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
