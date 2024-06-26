package com.example.wearwise;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
    private PostsListFragmentViewModel viewModel;
    String city= "";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPostRecyclerListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.postRecyclerView.setHasFixedSize(true);
        binding.postRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        //Model.instance().getPostByCity(city,(data)->{
            binding.postCitySpinner.setAdapter(SpinnerAdapter.setCitySpinner(getContext()));
            //binding.swipeRefresh.setOnRefreshListener(this::reloadData);

            binding.postCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    city=parent.getItemAtPosition(position).toString();
                    binding.postCity.setText(city);
                    //loadPostsByCity(city);
                    if(Objects.equals(city, "city")){
                        adapter = new PostAdapter( null, inflater);
                        binding.postRecyclerView.setAdapter(adapter);
                    }
                   else {
                        Model.instance().getPostByCity(city,(data)->{
                            adapter = new PostAdapter(data, getLayoutInflater());
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
        viewModel = new ViewModelProvider(this).get(PostsListFragmentViewModel.class);
    }

    private void loadPostsByCity(String city) {
        Model.instance().getPostByCity(city, posts -> {
            viewModel.setPostData(posts);
            adapter.setPostData(posts);
        });
    }

/*    private void reloadData() {
       // binding.swipeRefresh.setRefreshing(true); // Show refreshing indicator
        Model.instance().getRefreshPosts();
    }*/
}
