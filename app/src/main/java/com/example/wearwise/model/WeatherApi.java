package com.example.wearwise.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    String apiKey = "adf23b2696641c7f4c693b27f920c4ff";

    @GET("data/2.5/weather")
    Call<Weather> getWeatherByCityName(@Query("q") String city, @Query("appid") String apiKey);
}