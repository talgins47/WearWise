package com.example.wearwise.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wearwise.R;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder>{
    List<Post> postData;
    LayoutInflater inflater;

    public PostAdapter(List<Post> postData, LayoutInflater inflater) {
        this.postData = postData;
        this.inflater = inflater;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.futuer_weather_list_row, parent, false);
        //postData = Model.instance().getPost();
        return new PostViewHolder(inflate);
    }

    public void setPostData(List<Post> postData){
        this.postData = postData;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postData.get(position);
        holder.Bind(post, position);

    }

    @Override
    public int getItemCount() {
        return postData.size();
    }
}
