package com.example.wearwise.model;

import androidx.room.Entity;

@Entity
public class Post {
    private String postPicPath;
    private String city;

    public Post(String postPicPath, String city) {
        this.postPicPath = postPicPath;
        this.city = city;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostPicPath() {
        return postPicPath;
    }

    public void setPostPicPath(String postPicPath) {
        this.postPicPath = postPicPath;
    }
}
