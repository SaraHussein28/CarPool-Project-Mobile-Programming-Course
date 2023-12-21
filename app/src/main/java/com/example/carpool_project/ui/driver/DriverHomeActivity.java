package com.example.carpool_project.ui.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.carpool_project.R;

public class DriverHomeActivity extends AppCompatActivity {

    Button addTripButton, viewTripsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);
        findViewsById();
        addClickListeners();
    }

    private void addClickListeners() {
        addTripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DriverAddingTripsActivity.class);
                startActivity(intent);
            }
        });

        viewTripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DriverTripsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViewsById() {
    addTripButton = findViewById(R.id.button00);
    viewTripsButton = findViewById(R.id.button01);
    }
}