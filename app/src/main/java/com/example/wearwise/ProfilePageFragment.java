package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.wearwise.databinding.FragmentProfilePageBinding;
import com.example.wearwise.model.Model;


public class ProfilePageFragment extends Fragment {
    FragmentProfilePageBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= FragmentProfilePageBinding.inflate(inflater,container,false);
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