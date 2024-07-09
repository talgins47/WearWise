package com.example.wearwise.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wearwise.R;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>{
    List<Post> postData = new ArrayList<>();
    LayoutInflater inflater;


    public PostAdapter(List<Post> postData, LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = inflater.inflate(R.layout.search_post_row, parent, false);
        return new PostViewHolder(inflate);
    }

    public void setPostData(List<Post> postData){
        this.postData.clear();
        this.postData.addAll(postData);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postData.get(position);

        holder.bind(post, position);

    }

    @Override
    public int getItemCount() {
        if(postData == null) return 0;
        return postData.size();
    }
}
