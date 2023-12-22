package com.example.carpool_project.ui.mytrips;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carpool_project.databinding.FragmentMyTripsBinding;
import com.example.carpool_project.ui.database.WordViewModel;
import com.example.carpool_project.ui.driver.TripAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class MyTripsFragment extends Fragment {

//    private MyTripsViewModel mViewModel;
    private WordViewModel mWordViewModel;
    final String[] username = {""};

    FirebaseAuth auth;
    FragmentMyTripsBinding fragmentMyTripsBinding;

    public static MyTripsFragment newInstance() {
        return new MyTripsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        fragmentMyTripsBinding = FragmentMyTripsBinding.inflate(inflater, container, false);
        View root = fragmentMyTripsBinding.getRoot();
        auth = FirebaseAuth.getInstance();
//        getUserNameFromRoom(fragmentMyTripsBinding);
        retrieveTripsData(fragmentMyTripsBinding);

        return root;
    }
    private void retrieveTripsData(FragmentMyTripsBinding fragmentMyTripsBinding) {
        RecyclerView recyclerView = fragmentMyTripsBinding.recyclerView00;
        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getRiderTrips(auth.getCurrentUser().getUid()).observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            TripAdapter tripAdapter = new TripAdapter(words);
            recyclerView.setAdapter(tripAdapter);
        });
    }

//    private void getUserNameFromRoom(FragmentMyTripsBinding fragmentMyTripsBinding) {
//        String userEmail = auth.getCurrentUser().getEmail();
//        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
//        mWordViewModel.getUserData(userEmail).observe(getViewLifecycleOwner(), words -> {
//            if (words == null) {
//                Log.d("null words - rider main ", "null words - rider");
//            }
//            if (words != null){
//                Log.d("word info  - rider ", words.getEmail());
//                username[0] = words.getUsername();
//                retrieveTripsData(fragmentMyTripsBinding);
//            }
//        });
//    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MyTripsViewModel.class);
        // TODO: Use the ViewModel
    }

}