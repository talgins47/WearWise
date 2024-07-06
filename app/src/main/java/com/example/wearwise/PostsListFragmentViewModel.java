package com.example.wearwise;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.List;

public class PostsListFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Post>> postData = new MutableLiveData<>();

    public LiveData<List<Post>> getPostsByCity(String city) {

        return postData;
    }
}
