package com.example.videoshort_firebase;

import java.io.Serializable;

public class VideoModel implements Serializable {
    private String title;
    private String desc;
    private String url;
    private String userId;
    private String userEmail;
    private long timestamp;

    public VideoModel() {
        // Default constructor for Firebase
    }

    public VideoModel(String title, String desc, String url, String userId, String userEmail, long timestamp) {
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.userId = userId;
        this.userEmail = userEmail;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
