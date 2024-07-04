package com.natanshrestha.babybuy;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void addUser(User user);

    @Query("SELECT * FROM user WHERE email = :userEmail and password = :userPassword LIMIT 1")
    User getUser(String userEmail, String userPassword);

    @Query("SELECT * FROM user WHERE email=:userEmail")
    List<User> doesEmailExist(String userEmail);


    @Query("SELECT * FROM user WHERE email = :userEmail and name = :userName LIMIT 1")
    User getEmailName(String userEmail, String userName);

    @Query("SELECT * FROM user LIMIT 1")
    LiveData<User> getUserLiveData();
}