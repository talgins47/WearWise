package com.example.wearwise.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.wearwise.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Weather {

    //if a  name city has two names like Tel Aviv you should enter %20 between the names in the url
    @PrimaryKey
    @NonNull

    String description;
    String icon;
    double temp;
    double feelsLike;
    double tempMin;
    double tempMax;
    public Long LastUpdate;
    static double kelvinDegreeToCelsius = 273.15;

    public Weather(@NonNull String description, String icon, double temp, double feelsLike, double tempMin, double tempMax) {
        this.description = description;
        this.icon = icon;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
    }
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

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(double feelsLike) {
        this.feelsLike = feelsLike;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public Long getLastUpdate() {
        return LastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        LastUpdate = lastUpdate;
    }

    public static Map<String, Object> toJson(Weather weather) {
        Map<String, Object> json = new HashMap<>();
        json.put("description", weather.description);
        json.put("icon", weather.icon);
        json.put("temp", weather.temp);
        json.put("feelsLike", weather.feelsLike);
        json.put("tempMin", weather.tempMin);
        json.put("tempMax", weather.tempMax);
        json.put("lastUpdate", FieldValue.serverTimestamp());
        return json;
    }

    public static Weather fromJson(JsonObject json) {
        JsonObject main = json.getAsJsonObject("main");
        String description = json.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();
        String icon = json.getAsJsonArray("weather").get(0).getAsJsonObject().get("icon").getAsString();
        double temp = main.get("temp").getAsDouble() - kelvinDegreeToCelsius;
        double feelsLike = main.get("feels_like").getAsDouble()- kelvinDegreeToCelsius;
        double tempMin = main.get("temp_min").getAsDouble()- kelvinDegreeToCelsius;
        double tempMax = main.get("temp_max").getAsDouble()- kelvinDegreeToCelsius;

         Weather weather = new Weather(description, icon, temp, feelsLike, tempMin, tempMax);
     /*   try {
            Timestamp time = json.get("lastUpdate").();
            weather.setLastUpdate(time.getSeconds());
        }catch (Exception e) {
        }*/
        return weather;
    }
}
