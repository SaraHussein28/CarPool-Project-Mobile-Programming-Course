package com.example.carpool_project.ui.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.carpool_project.ui.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user ORDER BY username ASC")
    LiveData<List<User>> getAlphabetizedWords();

    @Query("SELECT * FROM user WHERE email= :userEmail")
    LiveData<User>getUserData(String userEmail);

}