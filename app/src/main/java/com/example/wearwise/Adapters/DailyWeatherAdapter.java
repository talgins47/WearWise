package com.example.wearwise.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wearwise.R;
import com.example.wearwise.model.DailyWeather;

import java.util.ArrayList;

public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyWeatherAdapter.ViewHolder> {

    ArrayList<DailyWeather> items;
    Context context;

    public DailyWeatherAdapter(ArrayList<DailyWeather> items){
        this.items = items;
    }
    @NonNull
    @Override
    public DailyWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.futuer_weather_list_row, parent, false);
        context = parent.getContext();

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherAdapter.ViewHolder holder, int position) {
        DailyWeather daily = items.get(position);



    }
    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, status, morningWeather, noonWeather, nightWeather;
        ImageView weatherPicPath, morningPicPath, noonPicPath, nightPicPath;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day= itemView.findViewById(R.id.dayText);
            status= itemView.findViewById(R.id.weatherType);
            morningWeather= itemView.findViewById(R.id.morningWeather);
            noonWeather= itemView.findViewById(R.id.noonWeather);
            nightWeather= itemView.findViewById(R.id.nightweather);
            weatherPicPath = itemView.findViewById(R.id.weatherPic);
            morningPicPath = itemView.findViewById(R.id.morningPic);
            noonPicPath = itemView.findViewById(R.id.noonPic);
            nightPicPath = itemView.findViewById(R.id.eveningPic);

        }

        public void Bind(DailyWeather daily){
         day.setText(daily.getDay());
         status.setText(daily.getStatus());
         morningWeather.setText(daily.getMorningWeather());
         noonWeather.setText(daily.getNoonWeather());
         nightWeather.setText(daily.getNightWeather());
        }
    }
}
