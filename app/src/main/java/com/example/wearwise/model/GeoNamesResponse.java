package com.example.wearwise.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GeoNamesResponse {
    @SerializedName("geonames")
    private List<City> geonames;

    public List<City> getGeonames() {
        return geonames;
    }
}
