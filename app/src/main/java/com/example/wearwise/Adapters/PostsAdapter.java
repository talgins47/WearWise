package com.example.wearwise.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wearwise.R;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Posts;

import java.util.List;

class PostsAdapter extends RecyclerView.Adapter<PostsViewHolder>{
    List<Posts> postsItems;
    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.futuer_weather_list_row, parent, false);
        postsItems = Model.instance().getPosts();
        return new PostsViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return postsItems.size();
    }
}
