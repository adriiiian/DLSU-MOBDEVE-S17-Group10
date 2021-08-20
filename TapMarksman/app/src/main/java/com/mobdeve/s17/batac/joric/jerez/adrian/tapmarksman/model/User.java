package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName = "";
    private String userEmail = "";
    private String userPassword = "";

    public User(){

    }

    public User(String userName, String userEmail, String userPassword){
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
