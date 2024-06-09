package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class logInFragment extends AppCompatActivity {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EditText logInemailEt = findViewById(R.id.logInEmail_et);
        EditText logInPsswrd = findViewById(R.id.loginPsswrd_et);
        Button logInBtn = findViewById(R.id.btnlogIn);
        Button signUpBtn = findViewById(R.id.signUpBtn_logIn);

        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        signUpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, signUpFragment.class);
            startActivity(intent);
        });

        return view;

    }
}