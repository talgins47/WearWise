package com.example.wearwise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.wearwise.databinding.FragmentProfilePageBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class ProfilePageFragment extends Fragment {
    FragmentProfilePageBinding binding;
    String fullName, username, city, bio;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    User user;
    //profileViewModel viewModel;
    View view;
/*
    ProfileListAdapter adapter;
*/
    UserViewModel userViewModel;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userViewModel=new ViewModelProvider(this).get(UserViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      binding = FragmentProfilePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
      /*    fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Model.instance().loadUserData(user.username, new Model.Listener<User>() {
            @Override
            public void onComplete(User data) {
                binding.fullName.setText(data.fullName);
                binding.username.setText(data.username);
                binding.bioProfile.setText(data.bio);
                binding.city.setText(data.city);

            }

        });*/
        userViewModel.getUser().observe(getViewLifecycleOwner(),newUser->{
            if (newUser!=null) {
                user = newUser;
                setProfile();
            }
        });
        binding.LogOutButton.setOnClickListener(v -> {
            Model.instance().logOut();
            Intent intent = new Intent(getActivity(), SignUpLogInActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        binding.editProfileButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_profilePageFragment_to_editProfile));
        return view;
    }

    public void setProfile(){
        binding.fullName.setText(user.fullName);
        binding.username.setText(user.username);
        binding.bioProfile.setText(user.bio);
        binding.city.setText(user.city);

    }

}
