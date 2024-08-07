package com.example.wearwise.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Query;

import com.example.wearwise.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity

public class Post {

    @PrimaryKey
    @NonNull
    public String id;
    public String city;
    public String describe;
    public String degree;
    public String postPicPath;
    public Long LastUpdate;
    public String username;


    public long getUploadPostTime() {
        return uploadPostTime;
    }


    public void setUploadPostTime(long uploadPostTime) {
        this.uploadPostTime = uploadPostTime;
    }

    public long uploadPostTime;

    public Post(){}

    public Post(String username,String postPicPath, String city, String describe, String degree, long uploadPostTime) {
        this.postPicPath = postPicPath;
        this.city = city;
        this.describe = describe;
        this.degree = degree;
        this.uploadPostTime = uploadPostTime;
        this.username = username;
    }
    static final String DESCRIBE = "describe";
    static final String DEGREE = "degree";
    static final String CITY = "city";
    static final String POST_PIC_PATH = "postPicPath";
    static final String COLLECTION = "Posts";
    static final String LAST_UPDATE = "lastUpdate";
    static final String LOCAL_LAST_UPDATE = "POSTLocalLastUpdate";

    public static Post fromJson(Map<String, Object> json){
        String username = (String) json.get("username");
        String postPicPath = (String) json.get("postPicPath");
        String city = (String) json.get("city");
        String describe = (String) json.get("describe");
        String degree = (String) json.get("degree");
        Post pt = new Post(username,postPicPath, city, describe, degree, 0);
        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATE);
            pt.setLastUpdate(time.getSeconds());
        }catch (Exception e) {
        }
        return pt;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharePref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharePref.getLong(LOCAL_LAST_UPDATE,0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharePref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putLong(LOCAL_LAST_UPDATE, time);
        editor.apply();
    }

    public static Map<String, Object> toJson(Post post){
        Map<String, Object> json = new HashMap<>();
        json.put("postPicPath", post.getPostPicPath());
        json.put("city", post.getCity());
        json.put("describe", post.getDescribe());
        json.put("degree", post.getDegree());
        json.put("lastUpdate", FieldValue.serverTimestamp());
        json.put("username", post.getUsername());
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

    public Long getLastUpdate(){
        return LastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
