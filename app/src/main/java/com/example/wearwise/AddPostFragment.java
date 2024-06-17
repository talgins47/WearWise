package com.example.wearwise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;


public class AddPostFragment extends Fragment {

    String city;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        EditText messageTv = view.findViewById(R.id.DescribePost);

        TextView degreeTv = view.findViewById(R.id.degreePost);
        Button PostBtn = view.findViewById(R.id.PostpostBtn);
        Spinner cityPostSpinner = view.findViewById(R.id.citySpinerPost);
        cityPostSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  city = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        cityPostSpinner.setAdapter(adapter);

        PostBtn.setOnClickListener(v -> {
            String message = messageTv.getText().toString();
            String degree = degreeTv.getText().toString();
            Post pt = new Post("", message, degree, city);
            Model.instance().addPost(pt,()->{

            });
            Intent intent = new Intent(getActivity(), SearchFragment.class);
            startActivity(intent);
        });

        return view;
    }
}