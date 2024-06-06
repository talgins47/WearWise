package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class signUpFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        TextView signInBtn = view.findViewById(R.id.signInBtn);

        signInBtn.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.imagePost));

        return view;
    }
}