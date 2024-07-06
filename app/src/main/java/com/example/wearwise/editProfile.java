package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.wearwise.databinding.FragmentEditProfileBinding;
import com.example.wearwise.databinding.FragmentLogInBinding;
import com.example.wearwise.databinding.FragmentSignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class editProfile extends Fragment {

    FragmentEditProfileBinding binding;
    FirebaseFirestore db;
    ProgressBar progressBar;

   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

     /*   // Retrieve user data from Firestore and populate EditText fields
        retrieveUserData();

        // Save changes button click listener
        binding.saveChangesBtn.setOnClickListener(v -> saveChanges());*/

        return binding.getRoot();
    }

   /* private void retrieveUserData() {
        if (currentUser != null) {
            DocumentReference docRef = db.collection("users").document(currentUser.getUid());
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    if (task.getResult() != null && task.getResult().exists()) {
                        String fullName = task.getResult().getString("fullName");
                        String userName = task.getResult().getString("userName");
                        String bio = task.getResult().getString("bio");
                        String city = task.getResult().getString("city");

                        // Set retrieved data into EditText fields
                        binding.fullNameEditText.setText(fullName);
                        binding.usernameEditText.setText(userName);
                        binding.bioEditText.setText(bio);
                        binding.editHomeCitySpinner.setSelection(getIndex(binding.editHomeCitySpinner, city));
                    } else {
                        Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to retrieve user data", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveChanges() {
        String fullName = binding.fullNameEditText.getText().toString().trim();
        String userName = binding.usernameEditText.getText().toString().trim();
        String bio = binding.bioEditText.getText().toString().trim();
        String city = binding.editHomeCitySpinner.getSelectedItem().toString();

        // Validate input
        if (fullName.isEmpty() || userName.isEmpty() || bio.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to update user data in Firestore
        Map<String, Object> userData = new HashMap<>();
        userData.put("fullName", fullName);
        userData.put("userName", userName);
        userData.put("bio", bio);
        userData.put("city", city);

        // Update user data in Firestore
        progressBar.setVisibility(View.VISIBLE);
        DocumentReference docRef = db.collection("users").document(currentUser.getUid());
        docRef.set(userData, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Failed to update profile: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Helper method to get the index of spinner item by value
    private int getIndex(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0; // Default to first item if not found
    }*/
}