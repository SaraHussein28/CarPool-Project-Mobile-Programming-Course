package com.example.carpool_project.ui.database;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.carpool_project.ui.dao.UserDao;
//import com.example.carpool_project.ui.dao.WordDao;
import com.example.carpool_project.ui.entities.User;
import com.example.carpool_project.ui.entities.Word;
import com.example.carpool_project.ui.helpers.RouteHelperClass;
import com.example.carpool_project.ui.helpers.UserHelperClass;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Repository {

//    private WordDao mWordDao;
    private UserDao mUserDao;
    private LiveData<List<Word>> mAllWords;
    private final MutableLiveData<ArrayList<RouteHelperClass>> mAllRoutes = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<RouteHelperClass>> driverTrips = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<RouteHelperClass>> riderTrips = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<String>> driverRidesIds = new MutableLiveData<>();
    private final MutableLiveData<UserHelperClass> userData = new MutableLiveData<>();


    private LiveData<List<User>> mAllUsers = new MutableLiveData<List<User>>();


        // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    Repository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
//        mWordDao = db.wordDao();
        mUserDao = db.userDao();
//        mAllWords = mWordDao.getAlphabetizedWords();
//        mAllUsers = mWordDao.getUserInfo();
        mAllUsers = mUserDao.getAlphabetizedWords();

        //TODO: implement a class for e remote data soruce.
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    LiveData<User> getUserData (String userEmail){
            return mUserDao.getUserData(userEmail);
    }

    LiveData<List<User>> getUserData (){
        return mAllUsers;
    }
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Word word) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
//            mWordDao.insert(word);
        });
    }

//    public LiveData<ArrayList<UserHelperClass>> getUserInfoFromFirebase (){
//
//TODO: add implementation for this.
//    }
    public MutableLiveData<ArrayList<RouteHelperClass>> getAllRoutes() {
//            MutableLiveData<ArrayList<RouteHelperClass>> mAllRoutes = null;
        ArrayList<RouteHelperClass> routesList = new ArrayList<>();

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference routesReference = rootNode.getReference("Routes").child("id");
        routesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // String value = dataSnapshot.getValue(String.class);
//                Iterable<DataSnapshot> routes = dataSnapshot.getChildren();
                for (DataSnapshot route : dataSnapshot.getChildren()) {
                    Log.d(TAG, "Value of route is: " + route.getValue().toString());
//                    RouteHelperClass routeHelperClass = createNewRoute(route);
                   // if (!Objects.equals(route.getValue(RouteHelperClass.class).getStatus(), "Cancelled")) {
                        routesList.add(route.getValue(RouteHelperClass.class));
                  //  }
                    Log.d(TAG, "Value of route converted is: " + route.getValue(RouteHelperClass.class));
                    Log.d(TAG, "Value of route list are ::: " + routesList.toString());
                }
                mAllRoutes.postValue(routesList);
                // Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return  mAllRoutes;
    }

    public void insertUser(User user) {
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mUserDao.insert(user);
        });
    }

    public MutableLiveData<ArrayList<String>> getDriverTripsIds(String driverID){
        ArrayList<String> routesIds = new ArrayList<>();

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference driverReference = rootNode.getReference("DriverTrips").child("driverID").child(driverID);
        driverReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot routeId : dataSnapshot.getChildren()) {
                    routesIds.add(routeId.getValue(String.class));
                    Log.d("ids", routeId.getValue(String.class));
                }
                driverRidesIds.postValue(routesIds);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return driverRidesIds;
    }
    public MutableLiveData<ArrayList<RouteHelperClass>> getDriverTrips(String driverID, ArrayList<String> tripsIds) {
        ArrayList<RouteHelperClass> routesList = new ArrayList<>();

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference routesReference = rootNode.getReference("Routes").child("id");
                routesReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot route : dataSnapshot.getChildren()) {
                            if (checkIntersection(route.getValue(RouteHelperClass.class).getRouteId(), tripsIds)) {
                              Log.d("ids in data change", route.getValue(RouteHelperClass.class).toString());
                                routesList.add(route.getValue(RouteHelperClass.class));
                            }
                        }
                        driverTrips.postValue(routesList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

              return driverTrips;
    }

    private boolean checkIntersection(String routeId, ArrayList<String> RidesIds) {
        Log.d("ids in check intersection - start", RidesIds.get(0));

        for (String driverRideId : RidesIds){
            Log.d("ids in check intersection", routeId);

            if (Objects.equals(driverRideId, routeId)){
                return true;
            }
        }
    return false;
    }

    public LiveData<ArrayList<RouteHelperClass>> getRiderTrips(String riderID) {
        ArrayList<RouteHelperClass> routesList = new ArrayList<>();

        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference driverReference = rootNode.getReference("RiderTrips").child("riderID").child(riderID);
        driverReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot route : dataSnapshot.getChildren()) {
                    routesList.add(route.getValue(RouteHelperClass.class));
                }
                riderTrips.postValue(routesList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return riderTrips;
    }

    public LiveData<UserHelperClass> getUserDataFromFirebase(String uid) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        DatabaseReference userReference = rootNode.getReference("Users").child(uid);
        Log.d("GET USER in repo", uid);
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                for (DataSnapshot route : dataSnapshot.getChildren()) {
//                    routesList.add(route.getValue(RouteHelperClass.class));
//                }
                UserHelperClass userHelperClass = dataSnapshot.getValue(UserHelperClass.class);
                userData.postValue(userHelperClass);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return userData;
    }

    public void updateTripStatus(String routeId, String status) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance("https://carpool-project-78ad3-default-rtdb.europe-west1.firebasedatabase.app");
        rootNode.getReference("Routes").child("id").child(routeId).child("status").setValue(status).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("completed", status);
            }

        });
    }

}