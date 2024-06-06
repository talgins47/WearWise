package com.example.wearwise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class home_pageFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        TextView mainPostBtn = view.findViewById(R.id.mainPostBtn);

        mainPostBtn.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.searchFragment));

        return view;
    }
}