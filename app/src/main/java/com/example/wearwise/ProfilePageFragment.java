package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.wearwise.databinding.FragmentProfileBinding;
import com.example.wearwise.databinding.FragmentSignUpBinding;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfilePageFragment extends Fragment {

    NavDirections action;
    FragmentProfileBinding binding;
    FirebaseAuth auth;
    ProgressBar processBar;

    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentProfileBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        binding.LogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getContext(), logInFragment.class);
                    startActivity(intent);
                    getActivity().finish();
                }
        });

        binding.editProfileButton.setOnClickListener(v-> {
            Intent intent = new Intent(getActivity(), editProfile.class);
            startActivity(intent);
            });
          return view;
    }
}