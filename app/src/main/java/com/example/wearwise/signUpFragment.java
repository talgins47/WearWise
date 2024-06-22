package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wearwise.databinding.FragmentSignUpBinding;
import com.example.wearwise.model.FireBaseModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class signUpFragment extends Fragment {
    NavDirections action;
    FragmentSignUpBinding binding;
    FirebaseAuth mAuth;
    ProgressBar processBar;

    String fullName;
    String userName;
    String email;
    String password;
    String city= "";

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSignUpBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        processBar = binding.signUpProgressBar;
        binding.SignUpbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fullName = binding.signUpNameEt.getText().toString();
                userName = binding.signUpUserEt.getText().toString();
                email= binding.signUpEmailEt.getText().toString();
                password = binding.SignUpPassWord.toString();
                processBar.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(getContext(), "Enter fullName", Toast.LENGTH_SHORT).show();
                return;
                }

                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(getContext(), "Enter userName", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getContext(), "Enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(), "Enter password", Toast.LENGTH_SHORT).show();
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                processBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getActivity(), MainActivity.class);
                                        startActivity(intent);
                                        getActivity().finish();
                                } else {
                                    Toast.makeText(getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });

        binding.LogInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               NavController navController = Navigation.findNavController(requireActivity(), R.id.main_navhost);
                navController.navigate(R.id.action_signUpFragment_to_logInFragment);

            }

        });



 /*       signInBtn.setOnClickListener(v-> {

            Navigation.createNavigateOnClickListener(action);
        });*/

       //action = signUpFragmentDirections;



      /*  signUpBtn.setOnClickListener(v-> {

            Navigation.createNavigateOnClickListener(action);
        });*/
        return view;
    }
 /*   public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
*/
    public boolean isEmailvalid(String email){
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    public void makeAToast(String text){
        new AlertDialog.Builder(getContext())
                .setTitle("Invalid Input")
                .setMessage(text)
                .setPositiveButton("Ok", (dialog,which)->{
                })
                .create().show();
    }

    public boolean validateInput(){

        if (!isEmailvalid(email)){
            makeAToast("Please enter a valid email");
            return false;
        }
        else if (Objects.equals(userName, "")) {
            makeAToast("Please enter an username");
            return false;

        } else if (Objects.equals(city, "city")) {
            makeAToast("Please choose a your home city");
            return false;
        } else if (password.length()<8) {
            makeAToast("Password must contains at least 8 characters");
            return false;
        }


        return true;
    }

}