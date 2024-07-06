/*
package com.example.wearwise;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.wearwise.model.Model;
import com.example.wearwise.model.User;

public class UserViewModel extends AndroidViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    public UserViewModel(Application application) {
        super(application);
    }

    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    public void fetchUser(String email) {
        Model.instance().fetchUserFromFirebase(email, user -> {
            if (user != null) {
                userLiveData.setValue(user);
            }
        });
    }

    public LiveData<User> getUserFromLocalDb(String email) {
        return Model.instance().getUserFromLocalDb(email);
    }
}*/
