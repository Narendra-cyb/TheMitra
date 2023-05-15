package com.example.themitra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationScreen extends AppCompatActivity {
    public static Location lastLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_screen);


        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Drawable upArrow = getResources().getDrawable(R.drawable.baseline_arrow_back_24);
        upArrow.setColorFilter(getResources().getColor(android.R.color.black), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        // Get a reference to the FusedLocationProviderClient
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Set a click listener on the button to retrieve the location
        Button buttonGetLocation = findViewById(R.id.auto_location);
        buttonGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check if the app has permission to access the device's location
                if (ContextCompat.checkSelfPermission(LocationScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // If permission is not granted, request it
                    ActivityCompat.requestPermissions(LocationScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                } else {
                    // If permission is granted, retrieve the location
                    retrieveLocation();
                }
            }
        });
        //Shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("LocationOpened", true).apply();
    }


    // Method to retrieve the current location
    private void retrieveLocation() {
        // Check if the app has permission to access the device's location
        if (ContextCompat.checkSelfPermission(LocationScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // If permission is granted, retrieve the location
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // If location retrieval is successful, send the location to the AccountFragment
                            if (location != null) {
                                lastLocation = location;
                                SharedPreferences sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putFloat("lastLatitude", (float) lastLocation.getLatitude());
                                editor.putFloat("lastLongitude", (float) lastLocation.getLongitude());
                                editor.apply();
                                Intent intent = new Intent(LocationScreen.this, TermsAndAgreementActivity.class);
                                startActivity(intent);
                            } else {
                                // If location retrieval is unsuccessful, display an error message
                                Toast.makeText(LocationScreen.this, "Unable to retrieve location", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    // Override the onRequestPermissionsResult method to handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // If permission is granted, retrieve the location
                retrieveLocation();
            } else {
                // If permission is not granted, display an error message
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // If the home button is clicked, navigate back to the IntroductionActivity
                Intent intent = new Intent(this, IntroductionActivity.class);
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