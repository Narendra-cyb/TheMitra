package com.example.themitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

public class LanguageSelectionActivity extends AppCompatActivity {

    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        Button englishButton = findViewById(R.id.english_button);
        Button hindiButton = findViewById(R.id.hindi_button);

        englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLanguage("english");
                startIntroductionActivity();
            }
        });

        hindiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLanguage("hindi");
                startIntroductionActivity();
            }
        });

        //Shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("languageOpened",true).apply();

    }

    private void saveLanguage(String language) {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("language", language);
        editor.apply();
    }

    private void startIntroductionActivity() {
        Intent intent = new Intent(LanguageSelectionActivity.this, IntroductionActivity.class);
        startActivity(intent);
        finish();
    }
}
