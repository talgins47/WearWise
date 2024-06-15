package com.example.wearwise;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wearwise.Adapters.DailyWeatherAdapter;
import com.example.wearwise.Adapters.PostAdapter;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.List;

public class PostRecyclerList extends Fragment {
     RecyclerView postList;
     List<Post> postData;
     PostAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_recycler_list, container, false);
        Model.instance().getPost((pCityList )->{
            postData = pCityList;
            adapter.setPostData(postData);
                },"New York");
        postList = view.findViewById(R.id.postRecyclerView);
        postList.setHasFixedSize(true);
        postList.setLayoutManager(new GridLayoutManager(getContext(),3));
        postList.setAdapter(new DailyWeatherAdapter());

        return view;

    }
}