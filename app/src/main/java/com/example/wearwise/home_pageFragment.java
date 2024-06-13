package com.example.wearwise;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.RecoverySystem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wearwise.Adapters.DailyWeatherAdapter;
import com.example.wearwise.model.Model;

public class home_pageFragment extends Fragment {
    private RecyclerView Dailylist;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        Dailylist = view.findViewById(R.id.future_weather_list);
        Dailylist.setHasFixedSize(true);
        Dailylist.setLayoutManager(new LinearLayoutManager(getActivity()));
        Dailylist.setAdapter(new DailyWeatherAdapter());

        return view;

    }
}
