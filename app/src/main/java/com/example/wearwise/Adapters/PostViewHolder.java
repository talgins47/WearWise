package com.example.wearwise.Adapters;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wearwise.ProfilePageFragmentDirections;
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
    ImageView editButton;
    ImageView deleteButton;


    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        this.postImage = itemView.findViewById(R.id.search_post_image_row);
        this.timeAgoTextView = itemView.findViewById(R.id.time_post);
        this.tempTextView = itemView.findViewById(R.id.temp_post);
        this.describeTextView = itemView.findViewById(R.id.describe_ago);
        this.editButton = itemView.findViewById(R.id.edit_btn);

    }

    public void bind(Post post, boolean isProfilePage) {
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
        tempTextView.setText(post.getDegree());
        describeTextView.setText(post.getDescribe());

        if (isProfilePage) {
            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(v -> {
                String postId = post.getUsername(); // Replace with your post ID getter
                NavDirections action = ProfilePageFragmentDirections.actionProfilePageFragmentToEditPostFragment();
                Navigation.findNavController(v).navigate(action);
            });
        } else {
            deleteButton.setVisibility(View.GONE);
            editButton.setVisibility(View.GONE);
        }
    }


}
