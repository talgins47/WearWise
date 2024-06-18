package com.example.wearwise.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private  Executor executor = Executors.newSingleThreadExecutor();
    private  Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FireBaseModel firebaseModel = new FireBaseModel();
    public static Model instance() {
        return _instance;
    }
    AppLocalDbRepository localDb= AppLocalDb.getAppDb();
       private Model(){}
    List<DailyWeather> dailyData = new LinkedList<>();

    public List<DailyWeather> getDailyWeather(){
        return dailyData;

    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public void getPostByCity(Listener<List<Post>> callback, String city) {
        firebaseModel.getPostByCity(callback, city);
/*        executor.execute(() -> {
            List<Post> data = localDb.postsDao().getPostByCity(city);
            mainHandler.post(()->{
                callback.onComplete(data);

            });
        });*/

    }

    public void addPost(Post post, Listener<Void> postListener){
        firebaseModel.addPost(post, postListener);
  /*      executor.execute(()->{
            localDb.postsDao().insertAll(post);
            mainHandler.post(()->{
                postListener.onComplete();
            });
        });*/
    }
    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name, bitmap, listener);
    }

    }
