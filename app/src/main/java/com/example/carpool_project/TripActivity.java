package com.example.carpool_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carpool_project.ui.cart.CartFrament;

public class TripActivity extends AppCompatActivity {

    private TextView pickUpTimeTextView;
    private TextView dropOffTimeTextView;
    private TextView sourcePointTextView;
    private TextView destinationPointTextView;
    private TextView priceTextView;
    private Button addToCartButton;

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

                Intent intent = new Intent(TripActivity.this, CartFrament.class);
                intent.putExtra("source", sourcePointTextView.getText());
                intent.putExtra("destination", destinationPointTextView.getText());
                intent.putExtra("drop_off_time", dropOffTimeTextView.getText());
                intent.putExtra("pick_up_time", pickUpTimeTextView.getText());
                intent.putExtra("price", priceTextView.getText());
                startActivity(intent);
            }
        });
    }

    private void updateViews() {
        String sourcePoint = getIntent().getStringExtra("source");
        String destinationPoint = getIntent().getStringExtra("destination");
        String pickUpTime = getIntent().getStringExtra("pick_up_time");
        String dropOffTime = getIntent().getStringExtra("drop_off_time");
        String tripFare = getIntent().getStringExtra("price");

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

