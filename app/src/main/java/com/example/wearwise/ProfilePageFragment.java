package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.wearwise.databinding.FragmentProfileBinding;
import com.example.wearwise.databinding.FragmentSignUpBinding;
import com.example.wearwise.model.Model;
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

        binding.LogOutButton.setOnClickListener(v -> {
            Model.instance().logOut();
            Intent intent = new Intent(getActivity(), SignUpLogInActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        binding.editProfileButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_profilePageFragment_to_editProfile));

        return view;
    }
}