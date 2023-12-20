package com.example.carpool_project;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    private final LiveData<List<Word>> mAllWords;
    private final LiveData<ArrayList<RouteHelperClass>> mAllRoutes;
    private final LiveData<List<User>> mALllUsers;

    public WordViewModel (Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllRoutes = mRepository.getAllRoutes();
        mAllWords = mRepository.getAllWords();
        mALllUsers = mRepository.getUserData();
    }

    public LiveData<List<Word>> getAllWords() { return mAllWords; }

    public LiveData<ArrayList<RouteHelperClass>> getAllRoutes() { return mAllRoutes; }
//    public LiveData<List<UserHelperClass>> getUserData(String userID) { return mRepository.getUserData(userID); }
    public LiveData<List<User>> getUserData() { return mALllUsers; }
    public LiveData<User> getUserData(String userEmail) { return mRepository.getUserData(userEmail); }

    public void insert(Word word) { mRepository.insert(word); }
    public void insertUser(User user) { mRepository.insertUser(user); }

}