package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.FirebaseCallback;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.FirebaseLeaderboardCallback;
import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;

public interface UserDAO {

    long addUser(User user);
    ArrayList<User> getUsers(FirebaseLeaderboardCallback firebaseLeaderboardCallback);
    void getUser(FirebaseCallback firebaseCallback);
    void updateUser(User user, FirebaseCallback firebaseCallback);
    void updateUserDifficulty(String difficulty, FirebaseCallback firebaseCallback);
    void updateUserGamePoints(int gamepoints, int highestScore, FirebaseCallback firebaseCallback);

}
