package com.example.wearwise;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.LinkedList;
import java.util.List;

public class PostsListFragmentViewModel extends ViewModel {
    private MutableLiveData<List<Post>> postData = new MutableLiveData<>();

    public PostsListFragmentViewModel() {
        // Initialize with all posts
        Model.instance().getAllPosts().observeForever(posts -> postData.setValue(posts));
    }

    LiveData<List<Post>> getPostData() {
        return postData;
    }

    public void setPostData(List<Post> posts) {
        postData.setValue(posts);
    }
}