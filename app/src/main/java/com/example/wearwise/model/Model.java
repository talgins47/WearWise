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
    AppLocalDbRepository localdb = AppLocalDb.getAppDb();
    public static Model instance() {
        return _instance;
    }
       private Model(){}
    List<DailyWeather> dailyData = new LinkedList<>();

    public List<DailyWeather> getDailyWeather(){
        return dailyData;

    }

    public interface Listener<T>{
        void onComplete(T data);
    }

    public void getRefreshPosts(){
        //get local last update
        Long localLastUpdate = Post.getLocalLastUpdate();
        //get all updated recorde from firebase since local lsat update
        firebaseModel.getAllPostSince(localLastUpdate,(list) ->{
           executor.execute(()->{
               Long time = localLastUpdate;
               for(Post pt: list) {
                   // insert new records into ROOM
                   localdb.postsDao().insertAll(pt);
                   if(time < pt.getLastUpdate()){
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
        executor.execute(()->{
            List<Post> complete = localdb.postsDao().getPostByCity(city);
            mainHandler.post(()->{
                callback.onComplete(complete);
            });

        });

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
