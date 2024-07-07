package com.example.wearwise;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

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

    FragmentEditProfileBinding binding;
    FirebaseFirestore db;
    ProgressBar progressBar;
    String city="";
    String postPicPath = "";
    String bio;
    String selectedCity="";

   // ActivityResultLauncher<Void> cameraAppLauncher;
    //ActivityResultLauncher<String> galleryAppLauncher;
    boolean photoSelected =false;
    UserViewModel userViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userViewModel=new ViewModelProvider(this).get(UserViewModel.class);

/*        cameraAppLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap o) {
                if (o != null) {
                    binding.profile.setImageBitmap(o);
                    photoSelected =true;
                }
            }
        });

        galleryAppLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                if (o != null) {
                    binding.avatar.setImageURI(o);
                    photoSelected =true;
                }
            }
        });
    }*/
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentEditProfileBinding.inflate(inflater,container,false);
        View v=binding.getRoot();
        //init postpicpath to the default image whitch is the profile in the drawable

    /*    binding.cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraAppLauncher.launch(null);
            }
        });

        binding.gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryAppLauncher.launch("image/*");
            }
        });*/

        userViewModel.getUser().observe(getViewLifecycleOwner(),(user)->{
            binding.bioEditText.setText(user.bio);
            if(!user.postPicPath.equals(""))
                Picasso.get().load(user.postPicPath).into(binding.editProfileIcon);
            else{
                binding.editProfileIcon.setImageResource(R.drawable.profile);
            }
            binding.editHomeCitySpinner.setAdapter(SpinnerAdapter.setCitySpinner(getContext()));
            binding.editHomeCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedCity = parent.getItemAtPosition(position).toString();
                    if (!Objects.equals(selectedCity, "city") && !Objects.equals(selectedCity, city)) {
                        city = selectedCity;
                    }
                    else{
                        selectedCity= user.city;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.saveChangesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (photoSelected) {
                        binding.editProfileIcon.setDrawingCacheEnabled(true);
                        binding.editProfileIcon.buildDrawingCache();
                        Bitmap bitmap = ((BitmapDrawable) binding.editProfileIcon.getDrawable()).getBitmap();
                        String id = UUID.randomUUID().toString();

                        Model.instance().uploadImage(id, bitmap, (url) -> {
                            User updatedUser = new User(user.fullName, user.username, user.email, selectedCity, postPicPath, binding.bioEditText.getText().toString());
                            updateUser(v, updatedUser);
                        });


                    } else {
                        User updatedUser = new User(user.fullName, user.username, user.email, selectedCity, postPicPath, binding.bioEditText.getText().toString());
                        updateUser(v, updatedUser);

                    }
                }
            });
        });

        return v;
    }

    public void updateUser(View v,User updatedUser){
        Model.instance().updateUser(updatedUser, unused ->{
                    Navigation.findNavController(v).popBackStack();

                });
    }
}