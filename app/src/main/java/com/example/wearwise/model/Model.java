package com.example.wearwise.model;

import java.util.LinkedList;
import java.util.List;

public class Model {
    private static final Model _instance = new Model();

    public static Model instance() {
        return _instance;
    }
       private Model(){}
    List<DailyWeather> data = new LinkedList<>();

    public List<DailyWeather> getDailyWeather(){
        return data;
    }

}
