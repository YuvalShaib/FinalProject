package com.example.finalproject;

public class Player {
    private String userName;
    private int score;


    public Player() {
    }

    public Player(String userName, int score) {
        this.userName = userName;
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public int getScore() {
        return score;
    }
}
