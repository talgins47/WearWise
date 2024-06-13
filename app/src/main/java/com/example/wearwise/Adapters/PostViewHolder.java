package com.example.wearwise.Adapters;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wearwise.R;
import com.example.wearwise.model.Post;

public class PostViewHolder extends RecyclerView.ViewHolder{
    ImageView postPicPath;
    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        postPicPath= itemView.findViewById(R.id.search_post_image_row);
    }

    public void Bind(Post post) {
    }
}
