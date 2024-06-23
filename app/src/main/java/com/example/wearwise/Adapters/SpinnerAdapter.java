package com.example.wearwise.Adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.example.wearwise.model.Model;

import java.lang.reflect.Array;
import java.util.List;

public class SpinnerAdapter {
    public static  ArrayAdapter<String> setCitySpinner( Context context){
        List<String> cities=Model.instance().getCities();
        ArrayAdapter<String> citySpinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,cities);
        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return citySpinnerAdapter;

    }
}
