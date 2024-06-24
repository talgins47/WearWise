package com.example.wearwise.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.wearwise.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FireBaseModel firebaseModel = new FireBaseModel();
    AppLocalDbRepository localdb = AppLocalDb.getAppDb();

    public static Model instance() {
        return _instance;
    }

    private Model() {
    }

    List<DailyWeather> dailyData = new LinkedList<>();

    public List<DailyWeather> getDailyWeather() {
        return dailyData;

    }

    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        cities.add(0,"city");
        cities.add(1,"Tel Aviv");
        cities.add(2,"Kfar Sava");
        cities.add(3,"Netanya");
        cities.add(4,"lod");
        return cities;
    }

    public void isUserNameExist(String userName, Listener<Boolean> listener) {
        firebaseModel.isUserNameExist(userName, listener);
    }

    public void isEmailExist(String email, Listener<Boolean> listener) {
        firebaseModel.isEmailExist(email, listener);
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    private LiveData<List<Post>> postList;
    public LiveData<List<Post>> getAllPosts() {
        if (postList == null) {
            postList = localdb.postsDao().getAll();
        }
        return postList;
    }

    public void getRefreshPosts() {
        //get local last update
        Long localLastUpdate = Post.getLocalLastUpdate();
        //get all updated recorde from firebase since local lsat update
        firebaseModel.getAllPostSince(localLastUpdate, (list) -> {
            executor.execute(() -> {
                Long time = localLastUpdate;
                for (Post pt : list) {
                    // insert new records into ROOM
                    localdb.postsDao().insertAll(pt);
                    if (time < pt.getLastUpdate()) {
                        time = pt.getLastUpdate();
                    }
                }
                //update local last update
                Post.setLocalLastUpdate(time);
            });
        });
    }


    public void getPostByCity(Listener<List<Post>> callback, String city) {
        getRefreshPosts();
        //return complete list from ROOM
        executor.execute(() -> {
            List<Post> complete = localdb.postsDao().getPostByCity(city);
            mainHandler.post(() -> {
                callback.onComplete(complete);
            });

        });

    }

    public void addPost(Post post, Listener<Void> postListener) {
        firebaseModel.addPost(post,(Void)->{
            getRefreshPosts();
            postListener.onComplete(null);

        });
    }

    public void addUser(User user) {
        firebaseModel.addUser(user);
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }

    public void logIn(String username, String password, Listener<Boolean> listener){
        firebaseModel.logIn(username, password, listener);
    }
    public void createUser(User user,String password, Model.Listener<Boolean> listener){
        firebaseModel.createUser(user, password, listener);
    }
    public void logOut(){
        firebaseModel.signOut();

    }

    public boolean isUserLog(){
        return firebaseModel.isUserLog();
    }
}
