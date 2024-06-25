package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wearwise.databinding.FragmentLogInBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class logInFragment extends Fragment {
    FragmentLogInBinding binding;
    ProgressBar processBar;
    FirebaseAuth mAuth;
    String username;
    String password;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogInBinding.inflate(inflater, container, false);
        processBar = binding.logInProgressBar;

        binding.btnlogIn.setOnClickListener(v -> {
            String username = binding.logInUserNameEt.getText().toString().trim();
            String password = binding.loginPsswrdEt.getText().toString().trim();

            processBar.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(getContext(), "Please enter both username and password", Toast.LENGTH_SHORT).show();
                processBar.setVisibility(View.GONE); // Hide progress bar if fields are empty
            } else {
                // Perform login using Model instance (assuming Model is handling Firebase authentication)
                Model.instance().logIn(username, password, (isSuccessful) -> {
                    processBar.setVisibility(View.GONE); // Hide progress bar after login attempt

                    if (isSuccessful) {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), "Username or Password are Incorrect!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.signUpBtnLogIn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_logInFragment_to_signUpFragment));

        return binding.getRoot();
    }
}