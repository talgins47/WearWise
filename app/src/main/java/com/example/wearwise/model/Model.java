package com.example.wearwise.model;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    public static Model instance() {
        return _instance;
    }
    AppLocalDbRepository localDb= AppLocalDb.getAppDb();
       private Model(){}
    List<DailyWeather> dailyData = new LinkedList<>();

    public List<DailyWeather> getDailyWeather(){
        return dailyData;

    }

    public interface GetPostByCity{
        void onComplete(List<Post> data);
    }
    public void getCityPost(GetPostByCity callback, String city) {
        executor.execute(() -> {
            List<Post> data = localDb.postsDao().getPostByCity(city);
            mainHandler.post(()->{
                callback.onComplete(data);

            });
        });
    }
    public interface GetPostListener{
        void onComplete(List<Post> data);
    }
  /*  public void getPost(GetPostListener callback) {
        executor.execute(() -> {
            List<Post> data = localDb.postsDao().getPost();
            mainHandler.post(()-> {
                callback.onComplete(data);
            }
        }
    }*/

    public interface AddPostListener{
        void onComplete();
    }

    public void addPost(Post post, AddPostListener postListener){
        executor.execute(()->{
            localDb.postsDao().insertAll(post);
            mainHandler.post(()->{
                postListener.onComplete();
            });
        });
    }

}
