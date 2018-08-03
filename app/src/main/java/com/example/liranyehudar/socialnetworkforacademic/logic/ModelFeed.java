package com.example.liranyehudar.socialnetworkforacademic.logic;

public class ModelFeed {

    private int id;
    private int amountOfLikes;
    private int amountOfComments;

    //.. private student..//
    private String fullName;
    private String status;
    private String time;

    public ModelFeed(int id, int amountOfLikes, int amountOfComments, String fullName, String status, String time) {
        this.id = id;
        this.amountOfLikes = amountOfLikes;
        this.amountOfComments = amountOfComments;
        this.fullName = fullName;
        this.status = status;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int getAmountOfLikes() {
        return amountOfLikes;
    }

    public int getAmountOfComments() {
        return amountOfComments;
    }

    public String getFullName() {
        return fullName;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }
}
