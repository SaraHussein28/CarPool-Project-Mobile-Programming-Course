package com.example.carpool_project.ui.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.carpool_project.R;
import com.example.carpool_project.ui.database.WordViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class DriverTripsActivity extends AppCompatActivity {
    private WordViewModel mWordViewModel;
    final String[] username = {""};
    ArrayList<String> tripsIds = new ArrayList<>();

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_trips);
        auth = FirebaseAuth.getInstance();
//        getUserNameFromRoom();
        RetrieveTripsData(savedInstanceState);
    }

    private void RetrieveTripsData(Bundle savedInstanceState) {
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        Log.d("username is here ", auth.getCurrentUser().getUid() );
        mWordViewModel.getDriverTripsIds(auth.getCurrentUser().getUid()).observe(this, words -> {
                mWordViewModel.getDriverTrips(auth.getCurrentUser().getUid(), words).observe(this, trips -> {
                    // Update the cached copy of the words in the adapter.
                    TripAdapter tripAdapter = new TripAdapter(trips);
                    RecyclerView recyclerView = findViewById(R.id.recyclerView00);
                    recyclerView.setAdapter(tripAdapter);
                });

        });


    }

//    private void getUserNameFromRoom() {
//        String userEmail = auth.getCurrentUser().getEmail();
//        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
//        mWordViewModel.getUserData(userEmail).observe(this, words -> {
//            if (words == null) {
//                Log.d("null words - driver main ", "null words - driver");
//            }
//            if (words != null){
//                Log.d("word info  - driver ", words.getEmail());
//                username[0] = words.getUsername();
//                RetrieveTripsData();
//            }
//        });
//    }
}