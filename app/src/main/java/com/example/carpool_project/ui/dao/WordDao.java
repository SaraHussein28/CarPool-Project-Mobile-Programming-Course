package com.example.carpool_project.ui.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.carpool_project.ui.helpers.UserHelperClass;
import com.example.carpool_project.ui.entities.Word;

import java.util.List;

@Dao
public interface WordDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

//    @Query("SELECT * FROM word_table ORDER BY word ASC")
//    List<Word> getAlphabetizedWords();

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAlphabetizedWords();

//    @Query("SELECT * FROM word_table WHERE username = :userID")
//    LiveData<List<UserHelperClass>> getUserId(String userID);

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<UserHelperClass>getUserData();

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<UserHelperClass>getUserInfo();
}