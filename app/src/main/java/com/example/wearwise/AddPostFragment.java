package com.example.wearwise;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.wearwise.Adapters.SpinnerAdapter;
import com.example.wearwise.databinding.FragmentAddPostBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;
import com.example.wearwise.model.User;

import java.util.UUID;

public class AddPostFragment extends Fragment {

    String city = "";
    FragmentAddPostBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    Boolean isPicSelected = false;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Set up city spinner
        binding.citySpinerPost.setAdapter(SpinnerAdapter.setCitySpinner(getContext()));
        binding.citySpinerPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.PostpostBtn.setOnClickListener(view1 -> {
            String message = binding.DescribePost.getText().toString();
            String degreeText = binding.degreePost.getText().toString();
            int degree = Integer.MIN_VALUE;

            if (city.isEmpty() || city.equals("Select a city")) {
                Toast.makeText(getContext(), "Please select a city", Toast.LENGTH_SHORT).show();
                return;
            }

            if (message.isEmpty()) {
                Toast.makeText(getContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                degree = Integer.parseInt(degreeText);
            } catch (NumberFormatException e) {
                // Handle exception
            }

            if (degree < -50 || degree > 60) {
                Toast.makeText(getContext(), "Please enter a degree between -50 and 60", Toast.LENGTH_SHORT).show();
                return;
            }

            String degreeWithCelsius = degree + "°C";
            Post pt = new Post("", city, message, degreeWithCelsius);

            if (isPicSelected) {
                binding.imagePost.setDrawingCacheEnabled(true);
                binding.imagePost.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.imagePost.getDrawable()).getBitmap();
                String id = UUID.randomUUID().toString();
                Model.instance().uploadImage(id, bitmap, (url) -> {
                    if (url != null) {
                        pt.setPostPicPath(url);
                    }
                    Model.instance().addPost(pt, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            } else {
                Model.instance().addPost(pt, (unused) -> {
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.imagePost.setImageBitmap(result);
                    isPicSelected = true;
                }
            }
        });

        binding.cameraBtnPost.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.galleryBtnPost.setOnClickListener(view1 -> {
            // Add gallery image selection logic here
        });

        return view;
    }
}
