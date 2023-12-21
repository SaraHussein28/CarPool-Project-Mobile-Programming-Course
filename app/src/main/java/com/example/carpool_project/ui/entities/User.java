package com.example.carpool_project.ui.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "user")
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    private String username;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "userRole")
    private String userRole;

    @NonNull
    @ColumnInfo(name = "gender")
    private String gender;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    @NonNull
    @ColumnInfo(name = "phoneNumber")
    private String phoneNumber;

    @NonNull
    @ColumnInfo(name = "email")
    private String email;


    public User(String userRole, @NonNull String username, @NonNull String name, @NonNull String gender, @NonNull String password, @NonNull String phoneNumber, @NonNull String email) {
        this.userRole = userRole;
        this.username = username;
        this.name = name;
        this.gender = gender;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @NonNull
    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(@NonNull String userRole) {
        this.userRole = userRole;
    }
    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getGender() {
        return gender;
    }

    public void setGender(@NonNull String gender) {
        this.gender = gender;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@NonNull String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getWord(){return this.username;}
}