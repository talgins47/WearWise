package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class signUpFragment extends AppCompatActivity {
    protected void onCreate(Bundle savedInstancesState) {
        super.onCreate(savedInstancesState);
        setContentView(R.layout.fragment_sign_up);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EditText nameEt = findViewById(R.id.signUpName_et);
        EditText userEt = findViewById(R.id.signUpUser_et);
        EditText emailEt = findViewById(R.id.signUpEmail_et);
        EditText psswrdEt = findViewById(R.id.signUpPsswrd_et);
        EditText confrmpssEt = findViewById(R.id.signUpConpswrd_et);
        Button signUpBtn = findViewById(R.id.btnlogIn);
        Button signInBtn = findViewById(R.id.signInBtn);

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        signInBtn.setOnClickListener(v-> {
            Intent intent = new Intent(this, logInFragment.class);
            startActivity(intent);
        });


        return view;
    }
}