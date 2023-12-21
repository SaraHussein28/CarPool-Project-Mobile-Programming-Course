package com.example.carpool_project.ui.trip_details;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carpool_project.R;

public class TripActivity extends AppCompatActivity {

    private TextView pickUpTimeTextView;
    private TextView dropOffTimeTextView;
    private TextView sourcePointTextView;
    private TextView destinationPointTextView;
    private TextView priceTextView;
    private Button addToCartButton;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        initViews();
        updateViews();
        setClickListeners();
    }

    private void setClickListeners() {
        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(TripActivity.this, "Item Is Added To Basket", Toast.LENGTH_SHORT);
                toast.show();
                storeDataToSharedPref();
                finish();
            }
        });
    }

    private void storeDataToSharedPref() {
//        FragmentManager fragM = getSupportFragmentManager();
//        FragmentTransaction fragT = fragM.beginTransaction();

        SharedPreferences sharedPref = this.getSharedPreferences("application", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

//        CartFragment cartFragment = new CartFragment();
//        Bundle trip_args = new Bundle();
        //TODO: consider other data passing options.
        editor.putString("source", sourcePointTextView.getText().toString());
        editor.putString("destination", destinationPointTextView.getText().toString());
        editor.putString("pick_up_time", pickUpTimeTextView.getText().toString());
        editor.putString("drop_off_time", dropOffTimeTextView.getText().toString());
        editor.putString("price", priceTextView.getText().toString());
        editor.putString("date", date);
//        cartFragment.setArguments(trip_args);
        editor.apply();
//        fragT.replace(R.id.nav_host_fragment_content_first, cartFragment);
//        fragT.commit();
    }

    private void updateViews() {
        String sourcePoint = getIntent().getStringExtra("source");
        String destinationPoint = getIntent().getStringExtra("destination");
        String pickUpTime = getIntent().getStringExtra("pick_up_time");
        String dropOffTime = getIntent().getStringExtra("drop_off_time");
        String tripFare = getIntent().getStringExtra("price");
        date = getIntent().getStringExtra("date");

        sourcePointTextView.setText(sourcePoint);
        destinationPointTextView.setText(destinationPoint);
        pickUpTimeTextView.setText(pickUpTime);
        dropOffTimeTextView.setText(dropOffTime);
        priceTextView.setText(tripFare);
    }

    private void initViews() {
        pickUpTimeTextView = findViewById(R.id.textView06);
        dropOffTimeTextView = findViewById(R.id.textView07);
        sourcePointTextView = findViewById(R.id.textView00);
        destinationPointTextView = findViewById(R.id.textView01);
        addToCartButton = findViewById(R.id.button00);
        priceTextView = findViewById(R.id.textView04);
    }
}

