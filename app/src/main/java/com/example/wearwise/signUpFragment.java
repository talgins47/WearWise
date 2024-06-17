package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class signUpFragment extends Fragment {
    NavDirections action;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);


        EditText nameEt = view.findViewById(R.id.signUpName_et);
        EditText userEt = view.findViewById(R.id.signUpUser_et);
        EditText emailEt = view.findViewById(R.id.signUpEmail_et);
        EditText psswrdEt = view.findViewById(R.id.signUpPsswrd_et);
        EditText confrmpssEt = view.findViewById(R.id.signUpConpswrd_et);
        Button signUpBtn = view.findViewById(R.id.btnlogIn);
        Button signInBtn = view.findViewById(R.id.signInBtn);

        //action = signUpFragmentDirections.actionSignUpFragmentToLogInFragment();

        signInBtn.setOnClickListener(v-> {

            Navigation.createNavigateOnClickListener(action);
        });

       //action = signUpFragmentDirections;



        signUpBtn.setOnClickListener(v-> {

            Navigation.createNavigateOnClickListener(action);
        });
        return view;
    }
}