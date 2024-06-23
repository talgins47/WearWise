package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wearwise.databinding.FragmentEntryBinding;
import com.example.wearwise.databinding.FragmentLogInBinding;


public class entryFragment extends Fragment {
    FragmentEntryBinding binding;
    NavController navController;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEntryBinding.inflate(inflater,container,false);
        View view =binding.getRoot();
        binding.welcomeLogInButton.setOnClickListener( Navigation.createNavigateOnClickListener(R.id.action_entryFragment_to_logInFragment));

        binding.welcomeSignUpButton.setOnClickListener( Navigation.createNavigateOnClickListener(R.id.action_entryFragment_to_signUpFragment));
        return view;
    }
}