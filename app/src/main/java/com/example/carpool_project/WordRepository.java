package com.example.carpool_project;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.carpool_project.ui.UserDao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

class WordRepository {

    private WordDao mWordDao;
    private UserDao mUserDao;
    private LiveData<List<Word>> mAllWords;
    private final MutableLiveData<ArrayList<RouteHelperClass>> mAllRoutes = new MutableLiveData<>();
    private LiveData<List<User>> mAllUsers = new MutableLiveData<List<User>>();


    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mUserDao = db.userDao();
        mAllWords = mWordDao.getAlphabetizedWords();
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
            mWordDao.insert(word);
        });
    }

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
                    routesList.add(route.getValue(RouteHelperClass.class));
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
}