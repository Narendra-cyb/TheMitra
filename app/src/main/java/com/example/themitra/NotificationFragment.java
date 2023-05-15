package com.example.themitra;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


public class NotificationFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        // Set the title of the toolbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.notificaton);

        // Get the toolbar from the layout
        Toolbar toolbar = view.findViewById(R.id.notification_toolbar);

        // Remove the logo from the toolbar
        toolbar.setLogo(null);

        // Remove any extra buttons from the toolbar
        toolbar.getMenu().clear();

        return view;

    }@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Clear the options menu
        menu.clear();
    }
}