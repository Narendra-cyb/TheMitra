package com.example.themitra;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;


public class TermsAndAgreementActivity extends AppCompatActivity {
    CheckBox checkBox;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_agreement);
        checkBox = findViewById(R.id.checkbox_agree);
        submit = findViewById(R.id.submit_button);

        Toolbar toolbar=findViewById(R.id.term_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the checkbox is checked
                if (checkBox.isChecked()) {
                    // Checkbox is checked, navigate to the next activity
                    startActivity(new Intent(TermsAndAgreementActivity.this, LoginActivity.class));
                    finish();
                } else {
                    // Checkbox is not checked, show a Toast message to the user
                    Toast.makeText(TermsAndAgreementActivity.this, "Please agree to the terms and conditions", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //Shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("TermOpened",true).apply();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // If the home button is clicked, navigate back to the IntroductionActivity
                Intent intent = new Intent(this, LocationScreen.class);
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