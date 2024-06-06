package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfilePageFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        Button editProfileBtn = view.findViewById(R.id.editProfileButton);


        editProfileBtn.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), editProfile.class);
            startActivity(intent);
            });
          return view;
    }
}