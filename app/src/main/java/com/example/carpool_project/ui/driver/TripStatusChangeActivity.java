package com.example.carpool_project.ui.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carpool_project.R;
import com.example.carpool_project.databinding.ActivityBoardingBinding;
import com.example.carpool_project.ui.database.WordViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class TripStatusChangeActivity extends AppCompatActivity {

    private static final String CONFIRMED = "Confirmed";
    private static final String CANCELLED = "Cancelled";

    private  TextView pickUpTimeTextView;
    private TextView dropOffTimeTextView;
    private  TextView sourcePointTextView;
    private TextView destinationPointTextView;

//    private final TextView tripStatus;
    private  TextView tripFareTextView;
    private TextView driverNameTextView;
    private ImageView confirmImageView, cancelImageView;
    private TextView confirmButton, cancelButton;
    private String date;
    private String routeId;
    private String status;
    private WordViewModel mWordViewModel;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_status_change);
        findViewsById();
        updateViews();
        setOnClickListeners();
    }

    private void findViewsById() {
        driverNameTextView = findViewById(R.id.textView00);
        pickUpTimeTextView = findViewById(R.id.textView08);
        dropOffTimeTextView = findViewById(R.id.textView10);
        sourcePointTextView = findViewById(R.id.textView09);
        destinationPointTextView = findViewById(R.id.textView11);
        confirmImageView = findViewById(R.id.imageView02);
        cancelImageView = findViewById(R.id.imageView05);
        confirmButton = findViewById(R.id.textView12);
        cancelButton = findViewById(R.id.textView14);
        tripFareTextView = findViewById(R.id.textView16);
    }

    private void updateViews() {
        String sourcePoint = getIntent().getStringExtra("source");
        String destinationPoint = getIntent().getStringExtra("destination");
        String pickUpTime = getIntent().getStringExtra("pick_up_time");
        String dropOffTime = getIntent().getStringExtra("drop_off_time");
        String tripFare = getIntent().getStringExtra("price");
        date = getIntent().getStringExtra("date");
        routeId = getIntent().getStringExtra("route_id");
        status = getIntent().getStringExtra("status");

        sourcePointTextView.setText(sourcePoint);
        destinationPointTextView.setText(destinationPoint);
        pickUpTimeTextView.setText(pickUpTime);
        dropOffTimeTextView.setText(dropOffTime);
        tripFareTextView.setText(tripFare);
        setDriverName();

    }

    private void setDriverName() {
        auth = FirebaseAuth.getInstance();
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getUserData(auth.getCurrentUser().getEmail()).observe(this, words -> {
            if (words == null) {
                Log.d("null words - status ", "null words - status");
            }
            if (words != null){
                Log.d("word info  - status ", words.getEmail());
                driverNameTextView.setText(words.getName());
            }
        });
    }

    private void setOnClickListeners() {
        confirmImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRouteStatus(CONFIRMED);
                Toast.makeText(getApplicationContext(), "successfully updated the status", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRouteStatus(CANCELLED);
                Toast.makeText(getApplicationContext(), "successfully updated the status", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void updateRouteStatus(String status) {
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.updateTripStatus(routeId,status);
    }
}