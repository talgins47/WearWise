package com.example.wearwise.Adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wearwise.R;
import com.example.wearwise.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class PostViewHolder extends RecyclerView.ViewHolder {
    ImageView postImage;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.postImage = itemView.findViewById(R.id.search_post_image_row);
    }

    public void bind(Post post, int pos) {
        if (Objects.equals(post.postPicPath, "")) {
            this.postImage.setImageResource(R.drawable.girl);
        } else {
            Picasso.get().load(post.postPicPath).into(this.postImage);
        }
    }
}
