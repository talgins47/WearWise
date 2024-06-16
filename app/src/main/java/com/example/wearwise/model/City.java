package com.example.wearwise.model;

import com.google.gson.annotations.SerializedName;

public class City {
    @SerializedName("toponymName")
    private String name;

    public String getName() {
        return name;
    }
}
