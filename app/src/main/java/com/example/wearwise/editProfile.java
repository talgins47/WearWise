package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class editProfile extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Button SaveBtn = view.findViewById(R.id.saveChangeBtn);

        SaveBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ProfilePageFragment.class);
            startActivity(intent);
        });
        return view;
    }
}