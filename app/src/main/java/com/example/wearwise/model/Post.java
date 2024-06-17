package com.example.wearwise.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import java.util.HashMap;
import java.util.Map;

@Entity

public class Post {
    @PrimaryKey
    @NonNull
    private String city;
    private String describe;
    private String degree;

    private String postPicPath;
    public Post(String postPicPath, String city, String describe, String degree) {
        this.postPicPath = postPicPath;
        this.city = city;
        this.describe = describe;
        this.degree = degree;
    }
    public static Post fromJson(Map<String, Object> json){
        String postPic = (String) json.get("postPic");
        String city = (String) json.get("city");
        String describe = (String) json.get("describe");
        String degree = (String) json.get("degree");
        Post pt = new Post(postPic, city, describe, degree);
        return pt;
    }
    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put("postPic", getPostPicPath());
        json.put("city", getCity());
        json.put("describe", getDescribe());
        json.put("degree", getDegree());
        return json;

    }


    @NonNull
    public String getCity() {
        return city;
    }

    public void setCity(@NonNull String city) {
        this.city = city;
    }

    public String getPostPicPath() {
        return postPicPath;
    }

    public void setPostPicPath(String postPicPath) {
        this.postPicPath = postPicPath;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
