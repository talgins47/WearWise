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

public class home_pageFragment extends AppCompatActivity {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        RecyclerView DailyWeatherList = findViewById(R.id.future_weather_list);
        futureList.setHasFixedSize(true);
        futureList.setLayoutManager(new LinearLayoutManager(this));

        return view;
    }

    class FutureViewHolder extends RecyclerView.ViewHolder{

        public FutureViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    class futureWeatherAdapter extends RecyclerView.Adapter<FutureViewHolder>{


        @NonNull
        @Override
        public FutureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.futuer_weather_list_row, null);

            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull FutureViewHolder holder, int position) {

    /*        TextView dayTv = view.findViewById(R.id.dayText);
            TextView weatherTv = view.findViewById(R.id.weatherType);
            TextView morningTv = view.findViewById(R.id.morningWeather);
            TextView noonTv = view.findViewById(R.id.noonWeather);
            TextView nightTv = view.findViewById(R.id.nightweather);*/

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}