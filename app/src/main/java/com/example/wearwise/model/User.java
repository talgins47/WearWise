package com.example.wearwise.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.example.wearwise.MyApplication;
import com.example.wearwise.R;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class User {

    @PrimaryKey
    @NonNull
    public String email;
    public String username;
    public String fullName;
    public String password;
    public String city;
    public Long LastUpdate;

    //public List<String> favorites;


    public User() {}

    public User(@NonNull String email, String username, String fullName, String password, String city) {
        this.email = email;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.city = city;
    }
    static final String FULL_NAME = "full_Name";
    static final String USERNAME = "user_Name";
    static final String EMAIL = "email";
    static final String PASSWORD = "password";
    static final String CITY = "city";
    static final String LAST_UPDATE = "lastUpdate";
    static final String LOCAL_LAST_UPDATE = "POSTLocalLastUpdate";


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


    public static User fromJson(Map<String, Object> json){
        String fullName = (String) json.get("full_name");
        String user_name = (String) json.get("user_name");
        String email = (String) json.get("email");
        String password = (String) json.get("password");
        String city = (String) json.get("city");
        User user = new User (fullName, user_name, email, password, city);
        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATE);
            user.setLastUpdate(time.getSeconds());
        }catch (Exception e) {
        }
        return user;
    }


    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(USERNAME, getUsername());
        json.put(FULL_NAME, getFullName());
        json.put(EMAIL, getEmail());
        json.put(PASSWORD, getPassword());
        json.put(CITY, getCity());
        json.put(LAST_UPDATE, FieldValue.serverTimestamp());
        return json;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        LastUpdate = lastUpdate;
    }


}
