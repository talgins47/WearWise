package com.example.wearwise;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class likePhotosFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like_photos, container, false);

        ListView likeList = view.findViewById(R.id.like_photo_list);

        return inflater.inflate(R.layout.fragment_like_photos, container, false);
    }
}