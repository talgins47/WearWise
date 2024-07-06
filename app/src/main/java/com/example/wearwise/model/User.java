package com.example.wearwise.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.wearwise.MyApplication;
import com.example.wearwise.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
@Entity

public class User {

    @PrimaryKey
    @NonNull
    public String email;
    public String username;
    public String fullName;
    public String city;
    public Long LastUpdate;
    public String postPicPath;
    public String bio;

    public User() {}

    public User(@NonNull String fullName, String username, String email, String city, String postPicPath, String bio) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.city = city;
        this.postPicPath = postPicPath;
        this.bio = bio;


    }
    static final String FULL_NAME = "fullName";
    static final String USERNAME = "userName";
    static final String EMAIL = "email";
    static final String PASSWORD = "password";
    static final String CITY = "city";
    static final String POST_PIC_PATH = "postPicPath";
    static final String BIO = "bio";
    static final String LAST_UPDATE = "lastUpdate";
    static final String LOCAL_LAST_UPDATE = "POSTLocalLastUpdate";

    static Long getUserlastUpdate() {
        SharedPreferences sharePref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharePref.getLong(LOCAL_LAST_UPDATE, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharePref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharePref.edit();
        editor.putLong(LOCAL_LAST_UPDATE, time);
        editor.apply();
    }

    public String getPostPicPath() {
        return postPicPath;
    }

    public void setPostPicPath(String postPicPath) {
        this.postPicPath = postPicPath;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public static Map<String, Object> toJson(User user){
        Map<String, Object> json=new HashMap<>();
        json.put("fullName",user.fullName);
        json.put("username",user.username);
        json.put("email",user.email);
        json.put("city",user.city);
        json.put("lastUpdate", FieldValue.serverTimestamp());
        json.put("postPicPath", user.postPicPath);
        json.put("bio", user.bio);

        return json;

    }

    public static User fromJson(Map<String, Object> json){
        String fullName = (String) json.get("fullName");
        String username = (String) json.get("username");
        String email = (String) json.get("email");
        String city = (String) json.get("city");
        String postPicPath = (String) json.get("postPicPath");
        String bio = (String) json.get("bio");
        User user = new User(fullName, username, email, city, postPicPath, bio);
        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATE);
            user.setLastUpdate(time.getSeconds());
        }catch (Exception e) {
        }
        return user;
    }


}
