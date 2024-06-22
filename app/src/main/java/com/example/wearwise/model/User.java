package com.example.wearwise.model;

import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.room.PrimaryKey;

import com.example.wearwise.R;

public class User {

    @PrimaryKey
    @NonNull
    public String email;
    public String username;

    public String fullName;
    public String location;
    public String number;
    //public List<String> favorites;
}
