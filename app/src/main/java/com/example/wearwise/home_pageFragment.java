
package com.example.wearwise;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.RecoverySystem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wearwise.Adapters.DailyWeatherAdapter;
import com.example.wearwise.Adapters.PostAdapter;
import com.example.wearwise.databinding.FragmentHomePageBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.LinkedList;
import java.util.List;

public class home_pageFragment extends Fragment {
    private RecyclerView Dailylist;
    FragmentHomePageBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//
      binding = FragmentHomePageBinding.inflate(inflater, container, false);
//        Dailylist.setHasFixedSize(true);
//        Dailylist.setLayoutManager(new LinearLayoutManager(getContext()));
//        Dailylist.setAdapter(new DailyWeatherAdapter());
//        binding.mainPostBtn.setOnClickListener((v)->{
//
//    Dialog dialog = new DatePickerDialog(getContext(), (WeatherPicker, "hot as fuck")->{
//
//            },);
//
//
//        });
//
       return binding.getRoot();

    }


}

