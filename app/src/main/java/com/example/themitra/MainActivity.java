package com.example.themitra;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    //Fragments
    HomeFragment homeFragment= new HomeFragment();
    AccountFragment accountFragment= new AccountFragment();
    NotificationFragment notificationFragment= new NotificationFragment();

    FloatingActionButton addFloat ;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToolBar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setLogo(R.drawable.baseline_pages_24);
//        toolbar.setTitle(R.string.app_name);
//        setSupportActionBar(toolbar);


        //float action button
        addFloat = findViewById(R.id.fab_add);
        addFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });




        //bottom navigation view
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,homeFragment).commit();

        //notificationBadge
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notficaton);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(6);

        //when click bottom navigation view
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,homeFragment).commit();
                        return  true;
                    case R.id.notficaton:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,notificationFragment).commit();
                        return  true;
                    case R.id.account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,accountFragment).commit();
                        return  true;
                }

                return false;
            }
        });


        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean loginOpened = prefs.getBoolean("loginOpened", false);
        boolean otpOpened = prefs.getBoolean("OTPOpened",false);
        boolean introOpened = prefs.getBoolean("IntroOpened",false);
        boolean languageOpened = prefs.getBoolean("languageOpened",false);
        boolean termOpened = prefs.getBoolean("TermOpened",false);
        if (!loginOpened && !otpOpened && !introOpened && !languageOpened && !termOpened) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbarmenu, menu);
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        // Configure search view as needed
//        MenuItem inviteItem = menu.findItem(R.id.action_invite);
//        // Configure invite item as needed
//        return true;
//    }


    //floating action button Click popupp
    @SuppressLint("RestrictedApi")
    private void showPopup(View view) {
        PopupMenu popup = new PopupMenu(this, view);
        popup.getMenuInflater().inflate(R.menu.floating_button_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.general_news:
                        // handle general news click
                        return true;
                    case R.id.corona_update:
                        // handle corona update click
                        return true;
                    case R.id.politics:
                        // handle politics click
                        return true;
                    case R.id.police_station:
                        // handle police station click
                        return true;
                    case R.id.state_update:
                        // handle state update click
                        return true;
                    case R.id.local_update:
                        // handle local update click
                        return true;
                    case R.id.education:
                        // handle education click
                        return true;
                    default:
                        return false;
                }
            }
        });

        popup.show();
    }
}
