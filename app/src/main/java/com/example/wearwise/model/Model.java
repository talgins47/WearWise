package com.example.wearwise.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FireBaseModel firebaseModel = new FireBaseModel();
    AppLocalDbRepository localdb = AppLocalDb.getAppDb();
    private final UserDao userDao;

    public static Model instance() {
        return instance;
    }


    private Model() {
        AppLocalDbRepository db = AppLocalDb.getAppDb();
        userDao = db.UserDao();
    }

    List<DailyWeather> dailyData = new LinkedList<>();

    public List<DailyWeather> getDailyWeather() {
        return dailyData;

    }

    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        cities.add(0,"city");
        cities.add(1,"Tel Aviv");
        cities.add(2,"Kfar Saba");
        cities.add(3,"Netanya");
        cities.add(4,"Jerusalem");
        cities.add(5,"Haifa");
        cities.add(6,"amsterdam ");
        cities.add(7,"Eilat");
        cities.add(8,"Berlin");
        cities.add(9,"moscow");
        cities.add(10,"new york");
        cities.add(11,"oslo");
        cities.add(12,"kopenhagen");





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
   /* public LiveData<List<Post>> getAllPosts() {
        if (postList == null) {
            postList = localdb.postsDao().getAll();
        }
        return postList;
    }
*/
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
                Post.setLocalLastUpdate(time);
            });
        });
    }

    public void getPostByCity(String city,Listener<List<Post>> callback) {
        getRefreshPosts();
        executor.execute(() -> {
            List<Post> complete = localdb.postsDao().getPostByCity(city);
            mainHandler.post(() -> {
                callback.onComplete(complete);
            });
        });
    }
/*     List<Post> post = new ArrayList<>();
      post.add(new Post("", city, "hello all you sucker", "50"));
     callback.onComplete(post);
    }*/

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
/*    public void currentUserInfo(){
        return firebaseModel.currentUserInfo():
    }*/

    public boolean isUserLog(){
        return firebaseModel.isUserLog();
    }



}