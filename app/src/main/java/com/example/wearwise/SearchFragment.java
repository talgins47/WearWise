package com.example.wearwise;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.media3.common.util.UnstableApi;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.wearwise.Adapters.SpinnerAdapter;
import com.example.wearwise.databinding.FragmentSearchBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.RetrofitClient;
import com.example.wearwise.model.Weather;
//import com.example.wearwise.model.WeatherApi;
import com.example.wearwise.model.Weather;
import com.example.wearwise.model.WeatherApi;
import com.example.wearwise.model.WeatherModel;
import com.google.gson.JsonObject;
//import com.example.wearwise.model.WeatherModel;

import java.text.DecimalFormat;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchFragment extends Fragment {

    String city="";
    FragmentSearchBinding binding;
    Dialog dialog;


    private final String appid = "adf23b2696641c7f4c693b27f920c4ff";
    DecimalFormat df  = new DecimalFormat("#.#");

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.degree.getText().toString();
        binding.citySearchSpinner.setAdapter(SpinnerAdapter.setCitySpinner(getContext()));
        binding.citySearchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
                String newCity = twoWordName(city);
                if(city.equals("city")){
                    binding.choseCity.setText("N/A");
                    binding.weatherDescribe.setText("no city chosen");
                    binding.degree.setText("__°C");
                }else {
                    binding.choseCity.setText(city);
                    WeatherModel.instance.getWeatherByCity(newCity, getContext(), new Model.Listener<Weather>() {
                        public void onComplete(Weather weather) {
                            binding.weatherDescribe.setText(weather.getDescription());
                            binding.degree.setText(String.valueOf(weather.getTemp()));
                            String iconUrl = "https://openweathermap.org/img/wn/" + weather.getIcon() + "@2x.png";
                            Glide.with(getContext()).load(iconUrl).into(binding.weatherIcon);

                            int drawableId = WeatherModel.instance.getDrawableForTemperature(weather.getTemp());
                            binding.ImageWeather.setImageResource(drawableId);

                            String suggestion = WeatherModel.instance.wearSuggestion(weather.getTemp());

                            Dialog dialog = new Dialog(getContext());
                            dialog.setContentView(R.layout.costum_wear_suggest);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_box);

                            TextView suggestionText = dialog.findViewById(R.id.suggestWear);
                            suggestionText.setText(suggestion);
                            binding.ImageWeather.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.show();
                                }
                            });

                     /*   binding.feelsLike.setText(String.valueOf(weather.getFeelsLike()));
                        binding.tempMin.setText(String.valueOf(weather.getTempMin()));
                        binding.tempMax.setText(String.valueOf(weather.getTempMax()));*/
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });


        return binding.getRoot();

    }
    public String twoWordName(String city){
        String newCity="";
        for (char c:city.toCharArray()){
            if(c==' '){
                newCity+="%20";
            }
            else{
                newCity+=c;
            }
        }
        return newCity;

    }

}


