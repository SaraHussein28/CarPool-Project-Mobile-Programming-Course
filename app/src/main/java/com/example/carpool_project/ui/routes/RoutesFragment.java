package com.example.carpool_project.ui.routes;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carpool_project.ui.helpers.RouteHelperClass;
import com.example.carpool_project.ui.database.WordViewModel;
import com.example.carpool_project.databinding.FragmentRoutesBinding;
import com.google.firebase.database.DataSnapshot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoutesFragment extends Fragment {
    private WordViewModel mWordViewModel;
    private final String FACULTY_POINT = "ASU, Faculty of Engineering - Gate 3";
    private FragmentRoutesBinding binding;
//    MutableLiveData< ArrayList<RouteHelperClass>> liveDataRoutesList;
//    ArrayList<RouteHelperClass> routesList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RoutesViewModel routesViewModel = new ViewModelProvider(this).get(RoutesViewModel.class);

        binding = FragmentRoutesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerView00;


        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllRoutes().observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            RouteAdapter routeAdapter = new RouteAdapter(words);
            recyclerView.setAdapter(routeAdapter);
        });

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    // move this to the trip details activity. when clicking on the add to cart button, add a check for the time to validate the time.
    private List<Character> filterResults(List<Character> characters, String source, String destination, String time) {
        List<Character> filteredCharacters = new ArrayList<>();

        // TODO: consider changing the data type for the time;
        LocalDateTime maxReservationTime;
        if (Objects.equals(source, FACULTY_POINT)) {
            maxReservationTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0));
        } else {
            maxReservationTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0));
        }

        for (int i = 0; i < characters.size(); ++i) {
            //ُTODO: remember to add a switch to bypass the time validation.

//            if (Objects.equals(characters.get(i).getSourcePoint(), source) && Objects.equals(characters.get(i).getDestinationPoint(), destination)) {
//                filteredCharacters.add(characters.get(i));
//            }
        }
        return filteredCharacters;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

//    private RouteHelperClass createNewRoute(DataSnapshot route) {
//        Log.d(TAG, "here is the source" + route.child("source").getValue().toString());
//        RouteHelperClass routeHelperClass = new RouteHelperClass(route.child("source").getValue().toString(), route.child("destination").getValue().toString(), route.child("day").getValue().toString(), route.child("pickupTime").getValue().toString(), route.child("dropOffTime").getValue().toString(), route.child("price").getValue().toString());
//        return routeHelperClass;
//    }
}
