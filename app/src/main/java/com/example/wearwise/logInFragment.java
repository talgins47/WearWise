package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wearwise.databinding.FragmentLogInBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class logInFragment extends Fragment {
    FragmentLogInBinding binding;
    ProgressBar processBar;
    FirebaseAuth mAuth;




    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentLogInBinding.inflate(inflater,container,false);
        processBar = binding.logInProgressBar;
        binding.btnlogIn.setOnClickListener(v -> {
            String username = binding.logInUserNameEt.getText().toString();
            String password = binding.loginPsswrdEt.getText().toString();
            processBar.setVisibility(View.VISIBLE);
            Model.instance().logIn(username, password, (isSuccessful) -> {
                processBar.setVisibility(View.GONE);
                if (isSuccessful) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                } else {
                    Toast.makeText(getContext(), "Username or Password are Incorrect!", Toast.LENGTH_SHORT).show();
                }

            });
        });
            binding.signUpBtnLogIn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_logInFragment_to_signUpFragment));

      return binding.getRoot();
    }

}
