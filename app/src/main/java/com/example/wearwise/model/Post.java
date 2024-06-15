package com.example.wearwise.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

@Entity
public class Post {
    private String postPicPath;
    @PrimaryKey
    @NonNull
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
