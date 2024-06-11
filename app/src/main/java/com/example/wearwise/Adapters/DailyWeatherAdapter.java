package com.example.wearwise.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wearwise.R;
import com.example.wearwise.model.DailyWeather;
import com.example.wearwise.model.Model;

import java.util.ArrayList;
import java.util.List;

class DailyViewHolder extends RecyclerView.ViewHolder {
    TextView day, status, morningWeather, noonWeather, nightWeather;
    ImageView weatherPicPath, morningPicPath, noonPicPath, nightPicPath;

    public DailyViewHolder(@NonNull View itemView) {
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
public class DailyWeatherAdapter extends RecyclerView.Adapter<DailyViewHolder> {

   List<DailyWeather> items;

    public DailyWeatherAdapter(){
        this.items = items;
    }
    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.futuer_weather_list_row, parent, false);
        items = Model.instance().getDailyWeather();
        return new DailyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
        DailyWeather daily = items.get(position);
        holder.Bind(daily);

    }
    @Override
    public int getItemCount() {
        return items.size();
    }

}
