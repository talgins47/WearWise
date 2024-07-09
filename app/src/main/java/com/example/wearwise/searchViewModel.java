package com.example.wearwise;

import androidx.lifecycle.ViewModel;

import com.example.wearwise.model.Post;

import java.util.List;

public class searchViewModel extends ViewModel {

    private List<Post> data;

    public List<Post> getData() {
        return data;
    }
    public void setData(List<Post> data){this.data=data;}

}

