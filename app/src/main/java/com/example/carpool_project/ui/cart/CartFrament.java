package com.example.carpool_project.ui.cart;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpool_project.R;

public class CartFrament extends Fragment {

    private CartFramentViewModel mViewModel;

    public static CartFrament newInstance() {
        return new CartFrament();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart_frament, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CartFramentViewModel.class);
        // TODO: Use the ViewModel
    }

//    private void updateViews() {
//        String sourcePoint = getIntent().getStringExtra("source");
//        String destinationPoint = getIntent().getStringExtra("destination");
//        String pickUpTime = getIntent().getStringExtra("pick_up_time");
//        String dropOffTime = getIntent().getStringExtra("drop_off_time");
//        String tripFare = getIntent().getStringExtra("price");
//
//        sourcePointTextView.setText(sourcePoint);
//        destinationPointTextView.setText(destinationPoint);
//        pickUpTimeTextView.setText(pickUpTime);
//        dropOffTimeTextView.setText(dropOffTime);
//        priceTextView.setText(tripFare);
//    }
//
//    private void initViews() {
//        pickUpTimeTextView = findViewById(R.id.textView06);
//        dropOffTimeTextView = findViewById(R.id.textView07);
//        sourcePointTextView = findViewById(R.id.textView00);
//        destinationPointTextView = findViewById(R.id.textView01);
//        addToCartButton = findViewById(R.id.button00);
//        priceTextView = findViewById(R.id.textView04);
//    }

}