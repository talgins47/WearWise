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

public class logInFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);

        EditText logIneMailEt = view.findViewById(R.id.logInEmail_et);
        EditText logInPsswrd = view.findViewById(R.id.loginPsswrd_et);
        Button logInBtn = view.findViewById(R.id.btnlogIn);
        Button signUpBtn = view.findViewById(R.id.signUpBtn_logIn);

        signUpBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), signUpFragment.class);
            startActivity(intent);
        });

        return view;

    }
}