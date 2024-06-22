package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.wearwise.databinding.FragmentSignUpBinding;


public class signUpFragment extends Fragment {
    NavDirections action;
    FragmentSignUpBinding binding;

    String fullName;
    String userName;
    String email;
    String password;
    String location= "";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSignUpBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        /*EditText nameEt = view.findViewById(R.id.signUpName_et);
        EditText userEt = view.findViewById(R.id.signUpUser_et);
        EditText emailEt = view.findViewById(R.id.signUpEmail_et);
        EditText psswrdEt = view.findViewById(R.id.signUpPsswrd_et);
        Button signInBtn = view.findViewById(R.id.signInBtn);*/

        //action = signUpFragmentDirections.actionSignUpFragmentToLogInFragment();



        binding.SignUpbtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                fullName = binding.signUpNameEt.getText().toString();
                userName = binding.signUpUserEt.getText().toString();
                email= binding.signUpEmailEt.getText().toString();
                password = binding.SignUpPassWord.toString();

            }
        });

        signInBtn.setOnClickListener(v-> {

            Navigation.createNavigateOnClickListener(action);
        });

       //action = signUpFragmentDirections;



        signUpBtn.setOnClickListener(v-> {

            Navigation.createNavigateOnClickListener(action);
        });
        return view;
    }


}