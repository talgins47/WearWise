package com.example.wearwise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.wearwise.Adapters.PostAdapter;
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
import com.squareup.picasso.Picasso;


public class ProfilePageFragment extends Fragment {
    private FragmentProfilePageBinding binding;
    private FirebaseAuth fAuth;
    private FirebaseFirestore fStore;
    private User user;
    UserViewModel userViewModel;
    PostsListViewModel postsListViewModel;
    String currentUsername;
    String username;
    PostAdapter adapter;
    RecyclerView postRecyclerList;
    searchViewModel viewModel;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel=new ViewModelProvider(this).get(searchViewModel.class);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfilePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userViewModel.getUser().observe(getViewLifecycleOwner(), newUser -> {
            if (newUser != null) {
                user = newUser;
                setProfile();
                username = user.getUsername();
                fetchUserPosts(username);
            }
        });
        Model.instance().getLoggedUserUsername().observe(getViewLifecycleOwner(), user -> {
            if (user != null && user.username != null) {
                String newUsername = user.username;
                this.username = newUsername;
            }
        });


        adapter = new PostAdapter(viewModel.getData(), inflater, true);
        postRecyclerList=binding.UserPostsList;
        postRecyclerList.setHasFixedSize(true);
        postRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        postRecyclerList.setAdapter(adapter);

        Model.instance().getPostsByUsername(username, (data) -> {
            viewModel.setData(data);
            adapter.setPostData(data);
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
    private void fetchUserPosts(String username) {
        adapter = new PostAdapter(viewModel.getData(), getLayoutInflater(), true);
        postRecyclerList = binding.UserPostsList;
        postRecyclerList.setHasFixedSize(true);
        postRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        postRecyclerList.setAdapter(adapter);

        Model.instance().getPostsByUsername(username, data -> {
            viewModel.setData(data);
            adapter.setPostData(data);
        });
    }


    private void setProfile() {
        binding.fullName.setText(user.getFullName());
        binding.username.setText(user.getUsername());
        binding.bioProfile.setText(user.getBio());
        binding.city.setText(user.getCity());
        if (!user.getPostPicPath().isEmpty()) {
            Picasso.get().load(user.getPostPicPath()).into(binding.profileIcon);
        } else {
            binding.profileIcon.setImageResource(R.drawable.profile);
        }
    }
}