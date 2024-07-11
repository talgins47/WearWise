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


    public LiveData<List<Post>> getPostsByCity(String city) {

        return postData;
    }
    public LiveData<List<Post>> getPostsByUsername(String username) {
        Model.instance().getPostsByUsername(username, posts -> {
            postData.setValue(posts);
        });
        return postData;
    }

    public void setPosts(List<Post> posts) {
        postData.setValue(posts);
    }

}
