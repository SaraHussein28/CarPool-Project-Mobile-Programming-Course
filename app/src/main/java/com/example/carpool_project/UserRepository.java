//package com.example.carpool_project;
//
//import static android.content.ContentValues.TAG;
//
//import android.app.Application;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.carpool_project.ui.UserDao;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class UserRepository {
//
//    private UserDao mUserDao;
//     private LiveData<UserHelperClass> mUser = new MutableLiveData<UserHelperClass>();
//
//
//    // Note that in order to unit test the WordRepository, you have to remove the Application
//    // dependency. This adds complexity and much more code, and this sample is not about testing.
//    // See the BasicSample in the android-architecture-components repository at
//    // https://github.com/googlesamples
//    UserRepository(Application application) {
//        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
//        mUserDao = db.userDao();
//
//        //TODO: implement a class for e remote data soruce.
//    }
//
//    // Room executes all queries on a separate thread.
//    // Observed LiveData will notify the observer when the data has changed.
////    LiveData<List<Word>> getAllUsers() {
////        return mAllWords;
////    }
//
//
//    LiveData<UserHelperClass> getUserData (String userID){
//        return mUserDao.getUserData(userID);
//    }
//    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
//    // that you're not doing any long running operations on the main thread, blocking the UI.
//    void insert(User user) {
//        UserRoomDatabase.databaseWriteExecutor.execute(() -> {
//            mUser.insert(user);
//        });
//    }
//
//}