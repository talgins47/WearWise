package com.example.wearwise;

import androidx.lifecycle.ViewModel;

import com.example.wearwise.model.Post;

import java.util.LinkedList;
import java.util.List;

public class PostsListFragmentViewModel extends ViewModel {
    private List<Post> postData = new LinkedList<>();

    List<Post> getPostData(){
        return postData;
    }

    void setPostData(List<Post> list){
        postData = list;
    }

}
