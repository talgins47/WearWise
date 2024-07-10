package com.example.wearwise;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostsListViewModel extends ViewModel {
    private MutableLiveData<List<Post>> postData = new MutableLiveData<>();
/*
    private MutableLiveData<List<Post> posts = new MutableLiveData<>();
*/

    public LiveData<List<Post>> getPostsByCity(String city) {

        return postData;
    }

  /*  public LiveData<List<Post>> getPostsByUsername(String username){
        return posts;
    }*/
}
