package com.example.wearwise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FutureWeatherFragment extends Fragment {
    private String day;
    private String picPath;
    private String status;
    private int Morning;
    private int Noon;
    private int Night;

    public FutureWeatherFragment(String day, String picPath, String status, int morning, int noon, int night) {
        this.day = day;
        this.picPath = picPath;
        this.status = status;
        Morning = morning;
        Noon = noon;
        Night = night;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMorning() {
        return Morning;
    }

    public void setMorning(int morning) {
        Morning = morning;
    }

    public int getNoon() {
        return Noon;
    }

    public void setNoon(int noon) {
        Noon = noon;
    }

    public int getNight() {
        return Night;
    }

    public void setNight(int night) {
        Night = night;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_future_weather, container, false);
    }
}