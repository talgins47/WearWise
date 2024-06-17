package com.example.wearwise;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wearwise.databinding.FragmentAddPostBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.UUID;


public class AddPostFragment extends Fragment {

    String city;
    FragmentAddPostBinding binding;
    ActivityResultLauncher<Void> cameraLaucher;
    Boolean isPicSelected = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.PostpostBtn.setOnClickListener(view1 -> {
            String message = binding.DescribePost.getText().toString();
            String degree = binding.degreePost.getText().toString();
            Post pt = new Post("",city, message, degree);

            if(isPicSelected){
                binding.imagePost.setDrawingCacheEnabled(true);
                binding.imagePost.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.imagePost.getDrawable()).getBitmap();
                String id = UUID.randomUUID().toString();
                Model.instance().uploadImage(id, bitmap, (url) -> {
                    if(url!=null){
                        pt.setPostPicPath(url);
                    }
                    Model.instance().addPost(pt,()->{
                        Navigation.findNavController(view1).popBackStack();
                    });

                });

            }else {
                Model.instance().addPost(pt, () -> {
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });

        binding.citySpinerPost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  city = parent.getItemAtPosition(position).toString();
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cameraLaucher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if(result!=null) {
                    binding.imagePost.setImageBitmap(result);
                    isPicSelected = true;
                }
            }
        });

        ArrayAdapter<String> citySpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.citySpinerPost.setAdapter(citySpinnerAdapter);

        binding.cameraBtnPost.setOnClickListener(view1->{
            cameraLaucher.launch(null);

        });
        binding.galleryBtnPost.setOnClickListener(view1->{

        });

        return view;
    }
}