package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;

public interface UserDAO {

    long addUser(User user);
    ArrayList<User> getUsers();
    User getUser(String userName, String userPassword);
    int updateUser(User user);
    int deleteUser(int userid);

}
