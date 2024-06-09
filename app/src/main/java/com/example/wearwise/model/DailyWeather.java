package com.example.wearwise.model;

public class DailyWeather {

    private String day;
    private String status;
    private String weatherPicPath;
    private String morningPicPath;
    private String noonPicPath;
    private String nightPicPath;
    private int morningWeather;
    private int noonWeather;
    private int nightWeather;

    public DailyWeather(String day, String status, String weatherPicPath, String morningPicPath, String noonPicPath, String nightPicPath, int morningWeather, int noonWeather, int nightWeather) {
        this.day = day;
        this.status = status;
        this.weatherPicPath = weatherPicPath;
        this.morningPicPath = morningPicPath;
        this.noonPicPath = noonPicPath;
        this.nightPicPath = nightPicPath;
        this.morningWeather = morningWeather;
        this.noonWeather = noonWeather;
        this.nightWeather = nightWeather;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWeatherPicPath() {
        return weatherPicPath;
    }

    public void setWeatherPicPath(String weatherPicPath) {
        this.weatherPicPath = weatherPicPath;
    }

    public String getMorningPicPath() {
        return morningPicPath;
    }

    public void setMorningPicPath(String morningPicPath) {
        this.morningPicPath = morningPicPath;
    }

    public String getNoonPicPath() {
        return noonPicPath;
    }

    public void setNoonPicPath(String noonPicPath) {
        this.noonPicPath = noonPicPath;
    }

    public String getNightPicPath() {
        return nightPicPath;
    }

    public void setNightPicPath(String nightPicPath) {
        this.nightPicPath = nightPicPath;
    }

    public int getMorningWeather() {
        return morningWeather;
    }

    public void setMorningWeather(int morningWeather) {
        this.morningWeather = morningWeather;
    }

    public int getNoonWeather() {
        return noonWeather;
    }

    public void setNoonWeather(int noonWeather) {
        this.noonWeather = noonWeather;
    }

    public int getNightWeather() {
        return nightWeather;
    }

    public void setNightWeather(int nightWeather) {
        this.nightWeather = nightWeather;
    }
}
