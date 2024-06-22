package com.example.wearwise;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.wearwise.model.Model;
import com.example.wearwise.model.Post;

import java.util.LinkedList;
import java.util.List;

public class PostsListFragmentViewModel extends ViewModel {
    private LiveData<List<Post>> postData = Model.instance().getAllPosts();

    LiveData<List<Post>> getPostData(){
        return postData;
    }


}
