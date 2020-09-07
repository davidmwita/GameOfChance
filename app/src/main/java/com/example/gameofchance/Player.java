package com.example.gameofchance;

import android.app.Application;

public class Player extends Application{

    private String name;
    private int credits;
    private int cost = 10;

    private static Player instance = null;

    public static Player getInstance() {
        if (instance == null) instance = new Player();
        return instance;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public int getDefaultCredits() {
        return 100;
    }

    public boolean deductCost() {
        if (credits >= cost) {
            credits -= cost;
            return true;
        }
        else return false;
    }

    public void addPoints(int reward) {
        credits += reward;
    }

}
