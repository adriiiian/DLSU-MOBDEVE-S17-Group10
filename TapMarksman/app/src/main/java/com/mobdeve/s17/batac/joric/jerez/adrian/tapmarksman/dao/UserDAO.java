package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.dao;

import com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model.User;

import java.util.ArrayList;

public interface UserDAO {

    long addUser(User user);
    ArrayList<User> getUsers();
    User getUser(int userid);
    int updateUser(User user);
    int deleteUser(int userid);

}
