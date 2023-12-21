package com.example.carpool_project.ui.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.carpool_project.R;
import com.example.carpool_project.databinding.FragmentRoutesBinding;
import com.example.carpool_project.ui.database.WordViewModel;
import com.example.carpool_project.ui.routes.RouteAdapter;
import com.example.carpool_project.ui.routes.RoutesViewModel;

public class DriverTrips extends AppCompatActivity {
    private WordViewModel mWordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_trips);


        RoutesViewModel routesViewModel = new ViewModelProvider(this).get(RoutesViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerView00);

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllRoutes().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            TripAdapter tripAdapter = new TripAdapter(words);
            recyclerView.setAdapter(tripAdapter);
        });

    }
}