package com.example.wearwise.model;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.wearwise.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherModel {
    final public static WeatherModel instance = new WeatherModel();

    final String BASE_URL = "https://api.openweathermap.org/";
    Retrofit retrofit;
    WeatherApi weatherApi;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";

    private WeatherModel() {
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        weatherApi = retrofit.create(WeatherApi.class);
    }
    public void getWeatherByCity(String city, Context context, Model.Listener<Weather> listener) {
        String tempUrl = url + "?q=" + city + "&appid=" + weatherApi.apiKey;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response != null) {
                        JsonObject json = JsonParser.parseString(response).getAsJsonObject();
                        Weather weather = Weather.fromJson(json);
                        listener.onComplete(weather);

                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            });
                    RequestQueue requestQueue = Volley.newRequestQueue(context);
                    requestQueue.add(stringRequest);
            }

    public String wearSuggestion(double temp) {
        String suggestion;

        if (temp < 9) {
            suggestion = "Wear a heavy coat, hat, or scarf and gloves.";
        } else if (temp >= 10 && temp <= 16) {
            suggestion = "Wear a coat and warm clothing.";
        } else if (temp >= 17 && temp <= 24) {
            suggestion = "A light jacket or hoodie should be enough.";
        } else if (temp >= 25 && temp <= 34) {
            suggestion = "Short wear,t-shirt and shorts with sandals";
        } else
            suggestion = "Stay hydrated and wear light clothing like tank top and flipflops.";

        return suggestion;
    }

    public int getDrawableForTemperature(double temp) {

        if (temp < 9) {
            return R.drawable.heavy_jacket;
        } else if (temp >= 10 && temp <= 16) {
            return R.drawable.jacket;
        } else if (temp >= 17 && temp <= 24) {
            return R.drawable.sweatshirt;
        } else if (temp >= 25 && temp <= 34) {
            return R.drawable.shirt3;
        } else
            return R.drawable.tank_shirt;

    }
}








