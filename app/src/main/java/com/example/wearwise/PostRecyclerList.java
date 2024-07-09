package com.example.wearwise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.wearwise.Adapters.PostAdapter;
import com.example.wearwise.Adapters.SpinnerAdapter;
import com.example.wearwise.databinding.FragmentPostRecyclerListBinding;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.List;
import java.util.Objects;

public class PostRecyclerList extends Fragment {

    private FragmentPostRecyclerListBinding binding;
    private PostAdapter adapter;
    private PostsListViewModel viewModel;
    private String city = "";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostRecyclerListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.postRecyclerView.setHasFixedSize(true);
        binding.postRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        binding.postCitySpinner.setAdapter(SpinnerAdapter.setCitySpinner(getContext()));

        binding.postCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city = parent.getItemAtPosition(position).toString();
                binding.postCity.setText(city);
                if (Objects.equals(city, "city")) {
                    adapter = new PostAdapter(null, inflater);
                    binding.postRecyclerView.setAdapter(adapter);
                } else {
                    viewModel.getPostsByCity(city).observe(getViewLifecycleOwner(), posts -> {
                        adapter = new PostAdapter(posts, getLayoutInflater());
                        binding.postRecyclerView.setAdapter(adapter);
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(PostsListViewModel.class);
    }
}
