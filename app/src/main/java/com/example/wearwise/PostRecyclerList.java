package com.example.wearwise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wearwise.Adapters.DailyWeatherAdapter;

public class PostRecyclerList extends Fragment {
     RecyclerView postList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        postList = view.findViewById(R.id.future_weather_list);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new LinearLayoutManager(getActivity()));
        postList.setAdapter(new DailyWeatherAdapter());

        return view;

    }
}