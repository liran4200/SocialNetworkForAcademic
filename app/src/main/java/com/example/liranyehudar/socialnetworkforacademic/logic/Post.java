package com.example.liranyehudar.socialnetworkforacademic.logic;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Post {

    private String key;
    private int likes;
    private int comments;
    private String fullName;
    private String status;
    private long createdTime ;
    private String studentId;

    public Post() {

    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public Post(String key, int likes, int comments, String fullName, String status, long createdTime, String studentId) {
        this.key = key;
        this.likes = likes;
        this.comments = comments;
        this.fullName = fullName;
        this.status = status;
        this.createdTime = createdTime;
        this.studentId = studentId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
