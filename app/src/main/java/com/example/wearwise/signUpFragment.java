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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wearwise.Adapters.SpinnerAdapter;
import com.example.wearwise.databinding.FragmentSignUpBinding;
import com.example.wearwise.model.FireBaseModel;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.User;
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentSignUpBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        processBar = binding.signUpProgressBar;
        binding.citySignUpSpinner.setAdapter(SpinnerAdapter.setCitySpinner(getContext()));
        binding.citySignUpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.SignUpbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                fullName = binding.signUpNameEt.getText().toString();
                userName = binding.signUpUserEt.getText().toString();
                email= binding.signUpEmailEt.getText().toString();
                password = binding.SignUpPassWord.toString();
                processBar.setVisibility(View.VISIBLE);

                if(validateInput()) {
                    Model.instance().isUserNameExist(userName,(userNameIsExist)->{
                        if (userNameIsExist) {
                            makeAToast("Username is Already Exist");
                        } else {
                            Model.instance().isEmailExist(email, (emailIsExist) -> {
                                if (emailIsExist) {
                                    makeAToast("Email Is Already Exist");
                                }
                            });
                        }

                    });
                }
                User user = new User(email, fullName, userName, password, city);
                Model.instance().createUser(user,(isSuccess)->{
                    if(isSuccess){
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                });
            }
        });
        binding.LogInBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_signUpFragment_to_logInFragment));


        return view;
    }

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