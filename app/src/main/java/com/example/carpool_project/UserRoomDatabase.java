//package com.example.carpool_project;
//
//import android.content.Context;
//
//import androidx.annotation.NonNull;
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//import androidx.sqlite.db.SupportSQLiteDatabase;
//
//import com.example.carpool_project.ui.UserDao;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//@Database(entities = {User.class}, version = 1, exportSchema = false)
//
//public abstract class UserRoomDatabase {
//    public abstract UserDao userDao();
//
//    private static volatile UserRoomDatabase INSTANCE;
//    private static final int NUMBER_OF_THREADS = 4;
//    static final ExecutorService databaseWriteExecutor =
//            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
//
//    static UserRoomDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (UserRoomDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                                    UserRoomDatabase.class, "user_database").addCallback(sRoomDatabaseCallback)
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }
//
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onCreate(@NonNull SupportSQLiteDatabase db) {
//            super.onCreate(db);
//
//            // If you want to keep data through app restarts,
//            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//                UserDao userDao = INSTANCE.userDao();
//                userDao.deleteAll();
//
//                User user = new User("userName",
//                        "nameText",
//                        "genderText",
//                        "passwordText",
//                        "phoneText",
//                        "emailText");
//                userDao.insert(user);
////                word = new Word("World");
////                dao.insert(word);
//            });
//        }
//    };
//}