package com.example.wearwise;

import android.app.AlertDialog;
import android.content.Intent;
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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wearwise.Adapters.SpinnerAdapter;
import com.example.wearwise.databinding.FragmentEditProfileBinding;
import com.example.wearwise.databinding.FragmentLogInBinding;
import com.example.wearwise.databinding.FragmentSignUpBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class editProfile extends Fragment {

    private FragmentEditProfileBinding binding;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private String city = "";
    private String postPicPath = "";
    private String bio;
    private String selectedCity = "";
    private Boolean isPicSelected = false;
    private boolean photoSelected = false;
    private UserViewModel userViewModel;

    private ActivityResultLauncher<Void> cameraAppLauncher;
    private ActivityResultLauncher<String> galleryAppLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        cameraAppLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.editProfileIcon.setImageBitmap(result);
                    isPicSelected = true;
                }
            }
        });

        galleryAppLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.editProfileIcon.setImageURI(result);
                    isPicSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        setupUI();

        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.bioEditText.setText(user.getBio());
            if (!user.getPostPicPath().isEmpty()) {
                Picasso.get().load(user.getPostPicPath()).into(binding.editProfileIcon);
            } else {
                binding.editProfileIcon.setImageResource(R.drawable.profile);
            }
            setupCitySpinner(user);
        });

        return view;
    }

    private void setupUI() {
        binding.cameraChangeProfile.setOnClickListener(view -> cameraAppLauncher.launch(null));

        binding.galleryBtnEdit.setOnClickListener(view -> galleryAppLauncher.launch("image/*"));

        binding.saveChangesBtn.setOnClickListener(view -> saveChanges());
    }

    private void setupCitySpinner(User user) {
        binding.editHomeCitySpinner.setAdapter(SpinnerAdapter.setCitySpinner(getContext()));
        binding.editHomeCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = parent.getItemAtPosition(position).toString();
                if (!Objects.equals(selectedCity, "city") && !Objects.equals(selectedCity, city)) {
                    city = selectedCity;
                } else {
                    selectedCity = user.getCity();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void saveChanges() {
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (isPicSelected) {
                binding.editProfileIcon.setDrawingCacheEnabled(true);
                binding.editProfileIcon.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.editProfileIcon.getDrawable()).getBitmap();
                String id = UUID.randomUUID().toString();

                Model.instance().uploadImage(id, bitmap, url -> {
                    if (url != null) {
                        user.setPostPicPath(url);
                    }
                    updateUser(user);
                });
            } else {
                updateUser(user);
            }
        });
    }

    private void updateUser(User user) {
        User updatedUser = new User(
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                selectedCity,
                user.getPostPicPath(),
                binding.bioEditText.getText().toString()
        );

        Model.instance().updateUser(updatedUser, unused -> {
            Navigation.findNavController(getView()).popBackStack();
        });
    }
}