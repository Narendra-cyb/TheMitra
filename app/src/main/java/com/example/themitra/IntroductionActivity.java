package com.example.themitra;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        ImageView imageView = findViewById(R.id.intro_image);
        imageView.setImageResource(R.drawable.splash_logo);

        FloatingActionButton buttonNext = findViewById(R.id.intro_button_add);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroductionActivity.this, LocationScreen.class));
                finish();
            }
        });

        //Shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("IntroOpened",true).apply();
    }

}