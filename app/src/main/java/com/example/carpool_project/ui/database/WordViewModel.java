package com.example.carpool_project.ui.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.carpool_project.ui.entities.User;
import com.example.carpool_project.ui.entities.Word;
import com.example.carpool_project.ui.helpers.RouteHelperClass;
import com.example.carpool_project.ui.helpers.UserHelperClass;

import java.util.ArrayList;
import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private Repository mRepository;

    private final LiveData<List<Word>> mAllWords;
    private final LiveData<ArrayList<RouteHelperClass>> mAllRoutes;
    private final LiveData<List<User>> mALllUsers;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new Repository(application);
        mAllRoutes = mRepository.getAllRoutes();
        mAllWords = mRepository.getAllWords();
        mALllUsers = mRepository.getUserData();
    }

    public LiveData<List<Word>> getAllWords() { return mAllWords; }

    public LiveData<ArrayList<RouteHelperClass>> getAllRoutes() { return mAllRoutes; }
    public LiveData<ArrayList<RouteHelperClass>> getDriverTrips(String driverID) { return mRepository.getDriverTrips(driverID); }

    public LiveData<ArrayList<RouteHelperClass>> getRiderTrips(String riderID) {
        return mRepository.getRiderTrips(riderID);
    }
    //    public LiveData<List<UserHelperClass>> getUserData(String userID) { return mRepository.getUserData(userID); }
    public LiveData<List<User>> getUserData() { return mALllUsers; }
    public LiveData<User> getUserData(String userEmail) { return mRepository.getUserData(userEmail); }
    public LiveData<UserHelperClass> getUserDataFromFirebase(String uid) {
        return mRepository.getUserDataFromFirebase(uid);
    }

    public void insert(Word word) { mRepository.insert(word); }
    public void insertUser(User user) { mRepository.insertUser(user); }


    public void updateTripStatus(String routeId, String status) {
        mRepository.updateTripStatus(routeId, status);
    }
}