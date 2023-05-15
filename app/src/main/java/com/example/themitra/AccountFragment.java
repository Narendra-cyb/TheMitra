package com.example.themitra;

import static android.content.Context.MODE_PRIVATE;
import static android.view.View.resolveSize;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.PopupMenuCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;
import java.util.NavigableMap;

public class AccountFragment extends Fragment {
    private TextView locationTextView;
    private TextView usernameTextView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;
    private double latitude;
    private double longitude;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imageView;
    String username,phone;
    String St,Ct;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        locationTextView = view.findViewById(R.id.location_textview);
        usernameTextView = view.findViewById(R.id.AccounttextView);
        imageView = view.findViewById(R.id.option_menu_button);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });
        tabLayout = view.findViewById(R.id.tab_layout);
        // initialize TabLayout and add tabs
        tabLayout.addTab(tabLayout.newTab().setText("Following"));
        tabLayout.addTab(tabLayout.newTab().setText("Followers"));
        tabLayout.addTab(tabLayout.newTab().setText("Post"));

// set custom view for each tab with label above
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tabView = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_layout, null);
            TextView label = tabView.findViewById(R.id.label);
            label.setText("0\n" + tabLayout.getTabAt(i).getText()); // set label with number and text
            tabLayout.getTabAt(i).setCustomView(tabView); // set custom view for tab
        }


        // Add a listener to the tabLayout to detect when the "Followers" tab is clicked
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if(position==0){
                    startActivity(new Intent(getActivity(), FollowingActivity.class));
                }
                else if(position==1){
                    startActivity(new Intent(getActivity(), FollwersActivity.class));
                }
                else if (position==2){
                    Toast.makeText(getActivity(), "POSTS", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing
            }
        });





        //toolbar
        toolbar = view.findViewById(R.id.account_toolbar);
        toolbar.setTitle("My Account");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        //navigation view
        // Find the drawer layout in the layout file
        drawerLayout = view.findViewById(R.id.drawer_layout);

        // Set up the action bar toggle for opening/closing the drawer
        toggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
//        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //setup the navigation drawer
        // Set up the navigation view
        navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                // Handle the item click and open the corresponding fragment
                switch (menuItem.getItemId()) {
                    case R.id.nav_settings:
                        // Open HomeFragment
                        Toast.makeText(getActivity(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_terms_conditions:
                        // Do nothing, we're already on the AccountFragment
                        Toast.makeText(getActivity(),"term and constionn",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_privacy_policy:
                        // Open SettingsFragment
                        Toast.makeText(getActivity(),"privacy policy Javatpoint",Toast.LENGTH_SHORT).show();
                        break;
                }

                // Close the drawer
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
        // Retrieve the user's information from the shared preference
        SharedPreferences sharedPref = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
        username = sharedPref.getString("username", "default_username"); // replace "default_username" with a default value
        phone = sharedPref.getString("phone_num", "default_location");
        float lastLatitude = sharedPref.getFloat("lastLatitude", 0);
        float lastLongitude = sharedPref.getFloat("lastLongitude", 0);

        Log.v("last","last "+ lastLatitude+" "+lastLongitude);

        // Display the user's information in the UI
        usernameTextView.setText(username);
        latitude = lastLatitude;
        longitude = lastLongitude;
        new GeocoderTask(getContext(), new GeocoderHandler()).execute(latitude, longitude);

        return view;
    }




    @SuppressLint("RestrictedApi")
    public void showPopup(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.option_menu_item, popupMenu.getMenu());

        // Set a listener for menu item clicks
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.option_edit:
                        Toast.makeText(getActivity(), "Edit is clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity() , Edit_Account_Activity.class);
                        intent.putExtra("username",username);
                        intent.putExtra("phone",phone);
                        intent.putExtra("state",St);
                        intent.putExtra("city",Ct);

                        startActivity(intent);
                        // Handle edit option click
                        return true;
                    case R.id.option_delete:
                        Toast.makeText(getActivity(), "Delete is clicked", Toast.LENGTH_SHORT).show();
                        // Handle delete option click
                        return true;
                    default:
                        return false;
                }
            }
        });

        // Get the menu popup helper and set its gravity to LEFT;
//        popupMenu.setGravity(Gravity.END);

        // Show the popup menu
        popupMenu.show();


    }

    private class GeocoderTask extends AsyncTask<Double, Void, List<Address>> {
        private final Context context;
        private final GeocoderHandler handler;

        public GeocoderTask(Context context, GeocoderHandler handler) {
            this.context = context;
            this.handler = handler;
        }

        @Override
        protected List<Address> doInBackground(Double... params) {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(params[0], params[1], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return addresses;
        }

        @Override
        protected void onPostExecute(List<Address> addresses) {
            if (addresses == null || addresses.size() == 0) {
                handler.onLocationNotFound("no address found");
            } else {
                Address address = addresses.get(0);
                String addressText = address.getAddressLine(0);
                String city = address.getLocality();
                Ct = city;
                String state = address.getAdminArea();
                St = state;
                String country = address.getCountryName();
                String postalCode = address.getPostalCode();
                handler.onLocationFound(addressText, city, state, country, postalCode);
            }
        }
    }

    private class GeocoderHandler {
        public void onLocationFound(String address, String city, String state, String country, String postalCode) {
            String locationString = "State: " + state + "\nPlace: " + city;
            locationTextView.setText(locationString);
        }

        public void onLocationNotFound(String errorMessage) {
            locationTextView.setText("Error: " + errorMessage);
        }
    }






}


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        // Clear the options menu
//        menu.clear();
//    }

