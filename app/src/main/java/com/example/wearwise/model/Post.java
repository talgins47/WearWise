package com.example.wearwise.model;

import androidx.room.Entity;

@Entity
public class Post {
    private String postPicPath;

    public Post(String postPicPath) {
        this.postPicPath = postPicPath;
    }

    public String getPostPicPath() {
        return postPicPath;
    }

    public void setPostPicPath(String postPicPath) {
        this.postPicPath = postPicPath;
    }
}
