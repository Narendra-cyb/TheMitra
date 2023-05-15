package com.example.themitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class OtpActivity extends AppCompatActivity {

    private EditText otpEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEditText = findViewById(R.id.otp_edit_text);

        AppCompatButton submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otpCode = otpEditText.getText().toString();
                if (otpCode.equals("1111")) {
                    startActivity(new Intent(OtpActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(OtpActivity.this, "Invalid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Shared preferences
//        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean isUserLoggedIn = prefs.getBoolean("isUserLoggedIn", false);
//
//        if (isUserLoggedIn) {
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        } else {
//            Intent intent = new Intent(this, LanguageSelectionActivity.class);
//            startActivity(intent);
//            finish();
//
//        }
    }
}
