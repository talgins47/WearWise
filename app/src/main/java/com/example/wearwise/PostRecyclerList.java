package com.example.wearwise;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wearwise.Adapters.DailyWeatherAdapter;
import com.example.wearwise.Adapters.PostAdapter;
import com.example.wearwise.databinding.FragmentPostRecyclerListBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.LinkedList;
import java.util.List;

public class PostRecyclerList extends Fragment {
     RecyclerView postList;
     PostAdapter adapter;
     FragmentPostRecyclerListBinding binding;
     PostsListFragmentViewModel viewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostRecyclerListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        //View view = inflater.inflate(R.layout.fragment_post_recycler_list, container, false);
        binding.postRecyclerView.setHasFixedSize(true);
        binding.postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PostAdapter(getLayoutInflater(), viewModel.getPostData());
        binding.postRecyclerView.setAdapter(adapter);


        Model.instance().getPostByCity((pCityList )->{
            postData = pCityList;
            adapter.setPostData(postData);
                },"New York");
        postList = view.findViewById(R.id.postRecyclerView);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new GridLayoutManager(getContext(),3));
        postList.setAdapter(new DailyWeatherAdapter());

        return view;

    }

    public void onAttach(@NonNull Context context){
         super.onAttach(context);
         viewModel = new ViewModelProvider(this).get(PostsListFragmentViewModel.class);
    }
}