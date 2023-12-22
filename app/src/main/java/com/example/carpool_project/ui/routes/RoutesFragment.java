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

    // this attribute is to disable the filtering.
    private static boolean BYPASS_FILTERING = false;
    private final String MAX_TIME_EVENING_RIDE = "5:30 pm";
    private final String MAX_TIME_MORNING_RIDE = "7:30am";
    String source, destination;

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
        getStringsFromBundle();

        mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
        mWordViewModel.getAllRoutes().observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            ArrayList<RouteHelperClass> routeHelperClasses = filterResults(words,source, destination);
            RouteAdapter routeAdapter = new RouteAdapter(routeHelperClasses);
            recyclerView.setAdapter(routeAdapter);
        });

//        final TextView textView = binding.textGallery;
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    private void getStringsFromBundle() {
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            source = bundle.getString("source", "defaultValue");
            destination = bundle.getString("destination", "defaultValue");
        }
        if (source.equals("defaultValue")) {
            BYPASS_FILTERING = true;
        }
    }

    // move this to the trip details activity. when clicking on the add to cart button, add a check for the time to validate the time.
    private ArrayList<RouteHelperClass> filterResults(ArrayList<RouteHelperClass> characters, String source, String destination) {
        if (BYPASS_FILTERING){
            return characters;
        }

        ArrayList<RouteHelperClass> filteredCharacters = new ArrayList<>();

        // TODO: consider changing the data type for the time;
        LocalDateTime maxReservationTime;
        if (Objects.equals(source, FACULTY_POINT)) {
            maxReservationTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0));
        } else {
            maxReservationTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 0));
        }

        for (int i = 0; i < characters.size(); ++i) {
            //ُTODO: remember to add a switch to bypass the time validation.
            String date = characters.get(i).getDay();
            String pickUpTime = characters.get(i).getPickupTime();
            LocalDate tripDate = parseDate(date);

            // add a condition for next day.
            if (Objects.equals(characters.get(i).getSource(), source) && Objects.equals(characters.get(i).getDestination(), destination)) {
                // add a condition to show only future trips, not older than today -> simply skip.

                // add a condition for the same day
                if (tripDate.isAfter(LocalDate.now().minusDays(1)) &&  pickUpTime.equals(MAX_TIME_EVENING_RIDE)){
                    if (LocalDateTime.now().isBefore(maxReservationTime)){
                        filteredCharacters.add(characters.get(i));

                    }
                }
                else if (tripDate.isAfter(LocalDate.now()) && pickUpTime.equals(MAX_TIME_MORNING_RIDE)){
                    filteredCharacters.add(characters.get(i));

                }

            }
        }
        return filteredCharacters;
    }

    private LocalDate parseDate(String date) {
        //TODO: add a valid implementation.
        LocalDate tripDate = LocalDate.now();

        String[] words = date.split("\\s+");
        if (words.length == 3) {
            String day, month, year;
            day = words[1];
            month = getMonth(words[0]);
            year = words[2];

            tripDate = LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        }
        return tripDate;
    }

    private String getMonth(String month) {
        if(month.equals("JAN"))
            return "1";
        if(month.equals("FEB"))
            return "2";
        if(month.equals("MAR"))
            return "3";
        if(month.equals("APR"))
            return "4";
        if(month.equals("MAY"))
            return "5";
        if(month.equals("JUN"))
            return "6";
        if(month.equals("JUL"))
            return "7";
        if(month.equals("AUG"))
            return "8";
        if(month.equals("SEP"))
            return "9";
        if(month.equals("OCT"))
            return "10";
        if(month.equals("NOV")) {
            return "11";
        }
        if(month.equals("DEC"))
            return "12";
        //default should never happen
        return "12";
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
