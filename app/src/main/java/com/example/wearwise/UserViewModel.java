package com.example.wearwise;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wearwise.model.Model;
import com.example.wearwise.model.User;

public class UserViewModel extends ViewModel {
    private LiveData<User> user;

    public LiveData<User> getUser() {
        if (user == null) {
            user = Model.instance().getLoggedUserUsername();
        }
        return user;
    }

    public void refreshUser() {
        loadUser();
    }

    private void loadUser() {
        // You can add any additional logic here if needed to refresh the user data
    }
}