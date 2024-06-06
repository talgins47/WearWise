package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class  PostPageFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_page, container, false);
        Button PostBtn = view.findViewById(R.id.PostpostBtn);

        PostBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchFragment.class);
            startActivity(intent);
        });

        return view;
    }
}