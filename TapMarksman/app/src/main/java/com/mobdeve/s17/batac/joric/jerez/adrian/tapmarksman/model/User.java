package com.mobdeve.s17.batac.joric.jerez.adrian.tapmarksman.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName = "";
    private String userEmail = "";
    private String userPassword = "";
    private Boolean isOwnedPistol;
    private Boolean isOwnedRevolver;
    private Boolean isOwnedDesertEagle;
    private Boolean isOwnedRifle;
    private int damageUpgradeCounter;
    private int powerUpgradeCounter;
    private int controlUpgradeCounter;
    private int multiplier;
    private int points;
    private String difficulty;
    private String equipedGun;

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

    public Boolean getOwnedPistol() {
        return isOwnedPistol;
    }

    public void setOwnedPistol(Boolean ownedPistol) {
        isOwnedPistol = ownedPistol;
    }

    public Boolean getOwnedRevolver() {
        return isOwnedRevolver;
    }

    public void setOwnedRevolver(Boolean ownedRevolver) {
        isOwnedRevolver = ownedRevolver;
    }

    public Boolean getOwnedDesertEagle() {
        return isOwnedDesertEagle;
    }

    public void setOwnedDesertEagle(Boolean ownedDesertEagle) {
        isOwnedDesertEagle = ownedDesertEagle;
    }

    public Boolean getOwnedRifle() {
        return isOwnedRifle;
    }

    public void setOwnedRifle(Boolean ownedRifle) {
        isOwnedRifle = ownedRifle;
    }

    public int getDamageUpgradeCounter() {
        return damageUpgradeCounter;
    }

    public void setDamageUpgradeCounter(int damageUpgradeCounter) {
        this.damageUpgradeCounter = damageUpgradeCounter;
    }

    public int getPowerUpgradeCounter() {
        return powerUpgradeCounter;
    }

    public void setPowerUpgradeCounter(int powerUpgradeCounter) {
        this.powerUpgradeCounter = powerUpgradeCounter;
    }

    public int getControlUpgradeCounter() {
        return controlUpgradeCounter;
    }

    public void setControlUpgradeCounter(int controlUpgradeCounter) {
        this.controlUpgradeCounter = controlUpgradeCounter;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getEquipedGun() {
        return equipedGun;
    }

    public void setEquipedGun(String equipedGun) {
        this.equipedGun = equipedGun;
    }
}
