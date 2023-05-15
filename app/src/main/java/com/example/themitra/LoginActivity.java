package com.example.themitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    EditText edtName, edtNum;
    AppCompatButton submit;
    String name,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtName = findViewById(R.id.edtName);
        edtNum = findViewById(R.id.edtPhone);

        submit = findViewById(R.id.sendotpBtn);

        //setup the toolbar

        int a = 6;
        String s = a+"";
        s.toString();

        Toolbar toolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString().trim();
                phone = edtNum.getText().toString().trim();
                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("username", name);
                editor.putString("phone_num", phone);
                editor.commit();
               // replace username with the user's username
                // replace location with the user's location


                Log.v("name and phone",name + phone);

                if (name.isEmpty()) {
                    edtName.setError("Name is required");
                    edtName.requestFocus();

                    return;
                }
                if (phone.isEmpty()) {
                    edtNum.setError("Phone number is required");
                    edtNum.requestFocus();
                    return;
                }
                if (phone.length() != 10) {
                    edtNum.setError("Invalid phone number");
                    edtNum.requestFocus();

                    return;
                }
                Intent intent = new Intent(LoginActivity.this, OtpActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);

                startActivity(intent);
            }
            
        });

        //Shared preferences
        // After successful login:
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean("isUserLoggedIn", true);
        ed.apply();






    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // If the home button is clicked, navigate back to the IntroductionActivity
                Intent intent = new Intent(this, TermsAndAgreementActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
        // Go back to the previous screen in the back stack
        super.onBackPressed();
    }
}