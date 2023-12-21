package com.example.carpool_project.ui.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.carpool_project.databinding.FragmentCartFramentBinding;
import com.example.carpool_project.ui.database.WordViewModel;
import com.example.carpool_project.ui.helpers.RouteHelperClass;
import com.example.carpool_project.ui.helpers.TripHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CartFragment extends Fragment {

    private TextView pickUpTimeTextView;
    private TextView dropOffTimeTextView;
    private TextView sourcePointTextView;
    private TextView destinationPointTextView;
    private TextView priceTextView;
    private Button proceedButton;
    private CartFramentViewModel mViewModel;
    private FragmentCartFramentBinding binding;
    String source, destination, pickUpTime, dropOffTime, trip_fare, date, status;
    private String routeId;
    private WordViewModel mWordViewModel;
    final String[] username = {""};

    FirebaseAuth auth;



    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentCartFramentBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        initViews();
        updateViews();
        getUserNameFromRoom();
        addClickListeners();
        return root;
    }

    private void addClickListeners() {
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // store
                storeRideIntoDatabase(source, destination, pickUpTime, dropOffTime, trip_fare, date, status);
                // make transition.

            }
        });
    }


    private void getUserNameFromRoom() {
        auth = FirebaseAuth.getInstance();
        String userEmail = auth.getCurrentUser().getEmail();
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getUserData(userEmail).observe(getViewLifecycleOwner(), words -> {
            if (words == null) {
                Log.d("null words - rider main ", "null words - rider");
            }
            if (words != null){
                Log.d("word info  - rider ", words.getEmail());
                username[0] = words.getUsername();
            }
        });
    }
    private void storeRideIntoDatabase(String source, String destination, String pickUpTime, String dropOffTime, String trip_fare, String date, String status) {
        RouteHelperClass route = new RouteHelperClass(routeId, status, source, destination, date, pickUpTime, dropOffTime, trip_fare);
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference driverTripsReference = rootNode.getReference("RiderTrips");
        DatabaseReference driverIdReference = driverTripsReference.child("riderID");
        driverIdReference.child(String.valueOf(username[0])).child(routeId).setValue(route).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "successfully inserted into database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "congrats");
                } else {
                    Toast.makeText(getActivity(), "Failed to insert into the database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "hard luck");

                }
            }
        });
    }


    private void updateViews() {

        try {
            SharedPreferences sharedPref = this.getActivity().getSharedPreferences("application", Context.MODE_PRIVATE);
            source = sharedPref.getString("source", "default");
            destination = sharedPref.getString("destination", "default");
            pickUpTime = sharedPref.getString("pick_up_time", "default");
            dropOffTime = sharedPref.getString("drop_off_time", "default");
            status = sharedPref.getString("status", "default");
            trip_fare = sharedPref.getString("price", "default");
            date = sharedPref.getString("date", "default");
            routeId = sharedPref.getString("route_id", "default");
            pickUpTimeTextView.setText(pickUpTime);
            dropOffTimeTextView.setText(dropOffTime);
            sourcePointTextView.setText(source);
            destinationPointTextView.setText(destination);
            priceTextView.setText(trip_fare);
        } catch (Exception e) {
            Log.d("exception in the cart", "cart");
        }

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
    private void initViews() {

        pickUpTimeTextView = binding.textView10;
        dropOffTimeTextView = binding.textView09;
        sourcePointTextView = binding.textView02;
        destinationPointTextView = binding.textView03;
        priceTextView = binding.textView05;
        proceedButton = binding.button00;
    }

}