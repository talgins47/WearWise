package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class entryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry, container, false);
        Button logInButton = view.findViewById(R.id.welcomeLogInButton);
        Button signUpButton = view.findViewById(R.id.welcomeSignUpButton);

        logInButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), logInFragment.class);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), signUpFragment.class);
            startActivity(intent);
        });

        return view;
    }
}