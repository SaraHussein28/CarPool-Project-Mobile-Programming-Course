package com.example.carpool_project.ui.driver;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.carpool_project.R;
import com.example.carpool_project.ui.database.WordViewModel;
import com.example.carpool_project.ui.helpers.RouteHelperClass;
import com.example.carpool_project.ui.helpers.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalTime;
import java.util.Objects;

public class DriverMainActivity extends AppCompatActivity {
    private final String FACULTY_POINT = "ASU, Faculty of Engineering - Gate 3";
    private final String MORNING_RIDE_TIME = "7:30 am";
    private final String EVENING_RIDE_TIME = "5:30 pm";
    private final String PENDING = "Pending", COMPLETED = "Completed", Confirmed = "Confirmed";
    String[] datesList = {"Now", "tomorrow", "Dec 3", "Dec 4", "Dec 5"};
    String[] durationList = {"10", "20", "30", "40", "60", "80", "100", "120"};
    String[] districtNames = {"Maadi", "Nasr City", "Zamalek", "Mohandseen", "Abassia Square", "ASU, Faculty of Engineering - Gate 3"};
    AutoCompleteTextView datesAutoCompleteTextView, durationAutoCompleteTextView, sourceAutoCompleteTextView, destinationAutoCompleteTextView;
    EditText priceEditText;
    String selectedDate, selectedDurationMin, selectedSource, selectedDestination;
    ArrayAdapter<String> datesArrayAdapter, durationArrayAdapter, districtArrayAdapter;
    ImageView imageView;
    Button submitButton;
    FirebaseAuth auth;
    private WordViewModel mWordViewModel;
    final String[] username = {""};
    private static int routeId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_main);
        auth = FirebaseAuth.getInstance();
        findViewsById();
        getUserNameFromRoom();
        setClickListeners();
        createArrayAdapters();
        setArrayAdapters();
    }

    private void findViewsById() {
        datesAutoCompleteTextView = findViewById(R.id.autoCompleteTextView00);
        durationAutoCompleteTextView = findViewById(R.id.autoCompleteTextView03);
        sourceAutoCompleteTextView = findViewById(R.id.autoCompleteTextView01);
        destinationAutoCompleteTextView = findViewById(R.id.autoCompleteTextView02);
        priceEditText = findViewById(R.id.editText00);
        imageView = findViewById(R.id.imageView00);
        submitButton = findViewById(R.id.button00);
    }

    private void setClickListeners() {
        datesAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedDate = item;
                Toast.makeText(DriverMainActivity.this, "Item " + item, Toast.LENGTH_SHORT).show();
            }
        });

        durationAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedDurationMin = item;
                Toast.makeText(DriverMainActivity.this, "Item " + item, Toast.LENGTH_SHORT).show();
            }
        });

        sourceAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedSource = item;
                Toast.makeText(DriverMainActivity.this, "Item " + item, Toast.LENGTH_SHORT).show();
            }
        });
        destinationAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedDestination = item;
                Toast.makeText(DriverMainActivity.this, "Item " + item, Toast.LENGTH_SHORT).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add the logic to swap the selected values in the two drop down lists.
//                String temp = sourceAutoCompleteTextView.getText().toString();
//                sourceAutoCompleteTextView.setText(destinationAutoCompleteTextView.getText());
//                destinationAutoCompleteTextView.setText(temp);
//                setArrayAdapters();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pickUpTime, dropOffTime, price;
                price = String.valueOf(priceEditText.getText());
                int durationHour = (Integer.parseInt(selectedDurationMin) + 30) / 60;
                int durationMinute = (Integer.parseInt(selectedDurationMin) + 30) % 60;
                LocalTime dropOffLocalTime = LocalTime.now();
                if (TextUtils.isEmpty(selectedDate)) {
                    Toast.makeText(DriverMainActivity.this, "Enter the trip date", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(selectedDurationMin)) {
                    Toast.makeText(DriverMainActivity.this, "Enter the trip duration", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(selectedSource)) {
                    Toast.makeText(DriverMainActivity.this, "Enter the start point", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(selectedDestination)) {
                    Toast.makeText(DriverMainActivity.this, "Enter your destination", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(price)) {
                    Toast.makeText(DriverMainActivity.this, "Enter your destination", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (Objects.equals(selectedDestination, FACULTY_POINT)) {
                    pickUpTime = MORNING_RIDE_TIME;
                    dropOffLocalTime = LocalTime.of(7 + durationHour, durationMinute);
                    dropOffTime = dropOffLocalTime.toString() + " am";

                } else {
                    pickUpTime = EVENING_RIDE_TIME;
                    dropOffLocalTime = LocalTime.of(17 + durationHour, durationMinute);
                    dropOffTime = dropOffLocalTime.toString() + " pm";
                }
                storeNewRouteData(selectedDate, pickUpTime, dropOffTime, selectedSource, selectedDestination, price);
                finish();
            }
        });

    }

    private void storeNewRouteData(String selectedDate, String pickUpTime, String dropOffTime, String selectedSource, String selectedDestination, String price) {
        RouteHelperClass route = new RouteHelperClass(String.valueOf(routeId), PENDING, selectedSource, selectedDestination, selectedDate, pickUpTime, dropOffTime, price);
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference routesReference = rootNode.getReference("Routes");
        DatabaseReference routeIdReference = routesReference.child("id");
        routeIdReference.child(String.valueOf(routeId++)).setValue(route).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "successfully inserted into database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "congrats");
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to insert into the database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "hard luck");

                }
            }
        });
        Log.d("driver username", username[0]);
        DatabaseReference driverTripsReference = rootNode.getReference("DriverTrips");
        DatabaseReference driverIdReference = driverTripsReference.child("driverID");
        String newRouteId = String.valueOf(routeId -1);
        driverIdReference.child(String.valueOf(username[0])).child(newRouteId).setValue(PENDING).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "successfully inserted into database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "congrats");
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to insert into the database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "hard luck");

                }
            }
        });

    }

    private void getUserNameFromRoom() {
        String userEmail = auth.getCurrentUser().getEmail();
            mWordViewModel = new ViewModelProvider(this).get(WordViewModel.class);
            mWordViewModel.getUserData(userEmail).observe(this, words -> {
                if (words == null) {
                    Log.d("null words - driver main ", "null words - driver");
                }
                if (words != null){
                    Log.d("word info  - driver ", words.getEmail());
                    username[0] = words.getUsername();
                }
            });
    }


    private void storeNewUserDate(String nameText, String userName, String genderText, String phoneText, String emailText, String passwordText) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference reference = rootNode.getReference("Users");

        UserHelperClass user = new UserHelperClass(nameText, userName, emailText, phoneText, passwordText, genderText);
        reference.child(userName).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "successfully inserted into database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "congrats");
                } else {
                    Toast.makeText(getApplicationContext(), "Failed to insert into the database", Toast.LENGTH_SHORT).show();
                    Log.i("success", "hard luck");

                }
            }
        });
    }

    private void createArrayAdapters() {
        datesArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, datesList);
        durationArrayAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, durationList);
        districtArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, districtNames);

    }

    private void setArrayAdapters() {

        datesAutoCompleteTextView.setAdapter(datesArrayAdapter);
        durationAutoCompleteTextView.setAdapter(durationArrayAdapter);
        sourceAutoCompleteTextView.setAdapter(districtArrayAdapter);
        destinationAutoCompleteTextView.setAdapter(districtArrayAdapter);

    }

}