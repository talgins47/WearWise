package com.example.wearwise;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.wearwise.Adapters.SpinnerAdapter;
import com.example.wearwise.databinding.FragmentEditPostBinding;
import com.example.wearwise.databinding.FragmentHomePageBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;
import com.example.wearwise.model.User;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class EditPostFragment extends Fragment {
    String city ;
    ActivityResultLauncher<Void> cameraLauncher;
    Boolean isPicSelected = false;
    User user;
    Post post;
    private FragmentEditPostBinding binding;
    private UserViewModel userViewModel;
    boolean isPhotoSelected;
    String username;
    private String selectedCity = "";

    private ActivityResultLauncher<Void> cameraAppLauncher;
    private ActivityResultLauncher<String> galleryAppLauncher;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Model.instance().getLoggedUserUsername().observe(getViewLifecycleOwner(), user -> {
            if (user != null && user.username != null) {
                String newUsername = user.username;
                this.username = newUsername;

                // Fetch the post to edit
                Model.instance().getPostsByUsername(username, (posts) -> {
                    if (posts != null && !posts.isEmpty()) {
                        // Assuming you want to edit the first post for simplicity
                        post = posts.get(0);

                        // Populate UI with post data
                        binding.DescribePost.setText(post.describe);
                        binding.degreePost.setText(post.degree + " °C"); // Ensure °C symbol is always appended

                    }
                });
            }
        });

        binding.saveEditBtn.setOnClickListener(v -> {
            post.describe = binding.DescribePost.getText().toString();
            post.degree = binding.degreePost.getText().toString().replace(" °C", ""); // Remove °C symbol for saving
            post.city = selectedCity;

            Model.instance().updatePost(post, (unused) -> {
                Navigation.findNavController(v).popBackStack(R.id.profilePageFragment, false);
            });
        });
        binding.deleteBtn.setOnClickListener((v) -> {
            new AlertDialog.Builder(getContext())
                    .setTitle("Warning!")
                    .setMessage("Are you sure you want to delete the post? ")
                    .setPositiveButton("yes", (dialog, which) -> {
                        Model.instance().deletePost(post, (unused) -> {
                            Navigation.findNavController(v).popBackStack(R.id.profilePageFragment, false);
                        });
                    }).setNegativeButton("No", (dialog, which) -> {
                    })
                    .create().show();

            InputFilter inputFilter = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                    try {
                        String input = dest.subSequence(0, dstart) + source.toString() + dest.subSequence(dend, dest.length());
                        int value = Integer.parseInt(input);
                        if (value >= -20 && value <= 50) {
                            return null; // Accept this number
                        }
                    } catch (NumberFormatException ignored) {
                    }
                    return "";
                }
            };

            binding.degreePost.setFilters(new InputFilter[]{inputFilter});

        });
        return view;

    }
}


