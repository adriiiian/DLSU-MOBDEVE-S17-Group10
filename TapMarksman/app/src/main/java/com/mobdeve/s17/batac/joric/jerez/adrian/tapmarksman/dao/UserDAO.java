package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.FirebaseCallback;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;

public interface UserDAO {

    long addUser(User user);
    ArrayList<User> getUsers();
    void getUser(FirebaseCallback firebaseCallback);
    long updateUser(User user, FirebaseCallback firebaseCallback);
    void updateUserDifficulty(String difficulty, FirebaseCallback firebaseCallback);
    void updateUserGamePoints(int gamepoints, FirebaseCallback firebaseCallback);
    int deleteUser(int userid);

}
