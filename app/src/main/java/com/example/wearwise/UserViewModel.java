package com.example.wearwise;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.wearwise.model.Model;
import com.example.wearwise.model.User;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    private  LiveData<User> user;

        public LiveData<User> getUser() {
            if (user == null) {
                user = Model.instance().getLoggedUserUsername();
            }
            return user;
        }

        private  void loadUser() {
        }

        public  void refreshUser() {
            loadUser();
        }
    }
