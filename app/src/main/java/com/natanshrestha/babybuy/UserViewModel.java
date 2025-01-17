package com.natanshrestha.babybuy;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Locale;

public class UserViewModel extends AndroidViewModel {
    private final UserDAO userDAO;
    private LiveData<User> userLiveData;

    public UserViewModel(@NonNull Application application) {
        super(application);
        Database database = Database.getDatabase(application);
        userDAO = database.userDAO();
        userLiveData = userDAO.getUserLiveData();
    }

    public void registerUser(User user) {
        userDAO.addUser(user);
    }

    public User getUser(String email, String password) {
        return userDAO.getUser(email.toLowerCase(), password);
    }

    public List<User> checkEmail(String email) {
        return userDAO.doesEmailExist(email.toLowerCase());
    }

    public User getNameEmail(String email, String name) {
        return userDAO.getUser(email.toLowerCase(), name);
    }
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

}