
package com.example.wearwise;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.RecoverySystem;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wearwise.Adapters.DailyWeatherAdapter;
import com.example.wearwise.Adapters.PostAdapter;
import com.example.wearwise.databinding.FragmentHomePageBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;
import com.example.wearwise.model.Weather;
import com.example.wearwise.model.WeatherModel;

import java.util.LinkedList;
import java.util.List;

public class home_pageFragment extends Fragment {
    private FragmentHomePageBinding binding;
    private UserViewModel userViewModel;
    Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // Observe the user data and fetch weather information
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null && user.getCity() != null) {
                binding.HomeLocation.setText(user.getCity());
                fetchWeatherData(user.getCity());
            }
        });

        return view;
    }

    private void fetchWeatherData(String city) {
        WeatherModel.instance.getWeatherByCity(city, getContext(), new Model.Listener<Weather>() {
            @Override
            public void onComplete(Weather weather) {
                if (weather != null) {
                    binding.currentTemp.setText(String.format("%.1f°C", weather.getTemp()));
                    binding.feelsLike.setText(String.format("Feels Like %.1f°C", weather.getFeelsLike()));

                    String iconUrl = "https://openweathermap.org/img/wn/" + weather.getIcon() + "@2x.png";
                    Glide.with(getContext()).load(iconUrl).into(binding.weatherHomeIcon);
                }
            }
        });
    }
}
