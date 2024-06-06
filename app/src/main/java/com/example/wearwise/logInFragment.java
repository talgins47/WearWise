package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class logInFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        Button signInBtn = view.findViewById(R.id.signUpBtn);

        signInBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), signUpFragment.class);
            startActivity(intent);
        });

        return view;

    }
}