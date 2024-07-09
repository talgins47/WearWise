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

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class PostViewHolder extends RecyclerView.ViewHolder {

    ImageView postImage;
    TextView timeAgoTextView;
    TextView tempTextView;
    TextView describeTextView;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.postImage = itemView.findViewById(R.id.search_post_image_row);
        this.timeAgoTextView = itemView.findViewById(R.id.time_post);
        this.tempTextView = itemView.findViewById(R.id.temp_post);
        this.describeTextView = itemView.findViewById(R.id.describe_ago);
    }

    public void bind(Post post, int pos) {
        if (Objects.equals(post.postPicPath, "")) {
            this.postImage.setImageResource(R.drawable.girl);
        } else {
            Picasso.get().load(post.postPicPath).into(this.postImage);
        }
        long now = System.currentTimeMillis();
        long uploadPostTime = post.getUploadPostTime(); // Ensure this method returns time in milliseconds
        long diff = now - uploadPostTime;
        long days = diff / (24 * 60 * 60 * 1000);
        long hours = (diff % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
        long minutes = (diff % (60 * 60 * 1000)) / (60 * 1000);
        String timeAgo = "";
        if (days > 0) {
            timeAgo = days + " days ago";
        }
        else if (hours > 0) {
            timeAgo = hours + " hours ago";
        }
        else if (minutes > 0) {
            timeAgo = minutes + " minutes ago";
        }
        else {
            timeAgo = "just now";
        }

        timeAgoTextView.setText(timeAgo);
    }


}
