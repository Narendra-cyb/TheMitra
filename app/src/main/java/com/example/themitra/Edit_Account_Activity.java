package com.example.themitra;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Edit_Account_Activity extends AppCompatActivity {
    TextInputEditText gendrEditText;
    TextInputEditText aboutmeEditText, stateEditText, cityEditText,TehsilEditText;
    TextInputEditText usernameEditText;
    TextInputEditText phoneEditText;
    String username, phone, city, state;
    ImageView profileimg;
    private static final int PICK_IMAGE = 100;


    AppCompatButton editbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Toolbar toolbar = findViewById(R.id.eidt_profiele_toolbar);
        // Set up the toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Profile");


        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        phone = intent.getStringExtra("phone");
        city = intent.getStringExtra("city");
        state = intent.getStringExtra("state");
        getSupportActionBar().setTitle("Profile");


        //TextInputEditText
        usernameEditText = findViewById(R.id.username_edit_text);
        phoneEditText = findViewById(R.id.phone_edit_text);
        gendrEditText = findViewById(R.id.gender_edit_text);
        aboutmeEditText = findViewById(R.id.about_edit_text);
        stateEditText = findViewById(R.id.state_edittext);
        cityEditText = findViewById(R.id.city_edittext);
        TehsilEditText = findViewById(R.id.tehsil_edittext);
        TehsilEditText.setText("");
        usernameEditText.setText(username);
        phoneEditText.setText(phone);
        stateEditText.setText(state);
        cityEditText.setText(city);

        //TextInputLayout
        TextInputLayout genderLayout = findViewById(R.id.gender_layout);
        TextInputLayout aboutLayout = findViewById(R.id.aboutme_layout);
        TextInputLayout usernameLayout = findViewById(R.id.username_layout);
        TextInputLayout phoneLayout = findViewById(R.id.phone_layout);

        //Gender
        gendrEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showGenderDialog();
            }
        });


        //image
        profileimg = findViewById(R.id.edit_profile);



        //Button
        editbtn = findViewById(R.id.edit_img_button);
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE);

            }
        });


        //About Me
        aboutmeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(aboutmeEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });


        //phone Edit Text
        phoneEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(phoneEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });


        TextInputEditText dateEditText = findViewById(R.id.date_edit_text);
        //date picker
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a Calendar instance to set the initial date in the DatePicker dialog
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog instance and show it
                DatePickerDialog datePickerDialog = new DatePickerDialog(Edit_Account_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Update the TextInputEditText text with the selected date
                                calendar.set(year, monthOfYear, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                String dateString = simpleDateFormat.format(calendar.getTime());
                                dateEditText.setText(dateString);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });


        usernameLayout.setHintAnimationEnabled(true);
        phoneLayout.setHintAnimationEnabled(true);
        genderLayout.setHintAnimationEnabled(true);
        aboutLayout.setHintAnimationEnabled(true);


        usernameLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        phoneLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        aboutLayout.setHintTextColor(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        usernameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(usernameEditText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

    }

    private void showGenderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Gender");

        // Create the radio buttons
        final RadioButton maleRadioButton = new RadioButton(this);
        maleRadioButton.setText("Male");
        maleRadioButton.setTextSize(20);
        maleRadioButton.setLayoutParams(getRadioButtonLayoutParams());

        final RadioButton femaleRadioButton = new RadioButton(this);
        femaleRadioButton.setText("Female");
        femaleRadioButton.setTextSize(20);
        femaleRadioButton.setLayoutParams(getRadioButtonLayoutParams());


        final RadioButton othersRadioButton = new RadioButton(this);
        othersRadioButton.setText("Others");
        othersRadioButton.setTextSize(20);
        othersRadioButton.setLayoutParams(getRadioButtonLayoutParams());

        // Create a radio group and add the radio buttons to it
        final RadioGroup radioGroup = new RadioGroup(this);
        radioGroup.addView(maleRadioButton);
        radioGroup.addView(femaleRadioButton);
        radioGroup.addView(othersRadioButton);

        // Set the checked radio button based on the current gender value
        if (gendrEditText.getText().toString().equals("Male")) {
            maleRadioButton.setChecked(true);

        } else if (gendrEditText.getText().toString().equals("Female")) {
            femaleRadioButton.setChecked(true);
        } else {
            othersRadioButton.setChecked(true);
        }

        // Set the dialog's view to the radio group
        // Set the dialog's view to the radio group with custom dimensions
        int width = 200; // in pixels
        int height = 450; // in pixels
        radioGroup.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        builder.setView(radioGroup);

        // Set the positive button to update the gender value
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = radioGroup.findViewById(selectedRadioButtonId);

                gendrEditText.setText(selectedRadioButton.getText());
            }
        });

        // Set the negative button to cancel the dialog
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Show the dialog
        builder.show();
    }

    private RadioGroup.LayoutParams getRadioButtonLayoutParams() {
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(40, 20, 0, 10); // Set margins to the radio button
        return params;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            // Save the changes
            saveChanges();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void saveChanges() {
        // Get the updated values from the form fields
        String updatedUsername = usernameEditText.getText().toString();
        String updatedPhone = phoneEditText.getText().toString();
        String updatedGender = gendrEditText.getText().toString();
        String updatedAboutMe = aboutmeEditText.getText().toString();
        String updatedState = stateEditText.getText().toString();
        String updatedCity = cityEditText.getText().toString();
     Intent intent = new Intent(this,AccountFragment.class);
     intent.putExtra("updateusername",updatedUsername);
     intent.putExtra("updatestate",updatedState);
     intent.putExtra("updatecity",updatedCity);
     intent.putExtra("updateabout",updatedAboutMe);
     startActivity(intent);




        // Save the updated values to the database or shared preferences
        // ...
    }

    //profile image picker
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Get the selected image's URI
            Uri imageUri = data.getData();

            // Set the image to the profile image view
            profileimg.setImageURI(imageUri);
        }
    }




}