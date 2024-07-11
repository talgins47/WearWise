package com.example.wearwise.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FireBaseModel firebaseModel = new FireBaseModel();
    AppLocalDbRepository localdb = AppLocalDb.getAppDb();
    private final UserDao userDao;
    public String username;


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

    private LiveData<User> user;

    public LiveData<User> getLoggedUserUsername(){

        // Get the logged user username from Firebase
        String username = firebaseModel.getLoggedUserUsername();

        // Check if the username is not null and set the class-level username variable
        if (username != null) {
            this.username = username;
        }

        // Check if the user LiveData is null
        if (user == null) {
            // Fetch user data from the local database based on the username
            user = localdb.UserDao().getUserByUsername(this.username);

            // Refresh all users from the remote database or other sources if necessary
            refreshAllUsers();
        }

        // Return the user LiveData
        return user;
    }

    public void refreshAllUsers(){
        Long localLastUpdate= User.getUserlastUpdate();
        firebaseModel.getAllUsersSince(localLastUpdate,(users)->{
            executor.execute(()->{
                Long time=localLastUpdate;
                for (User user : users) {
                    localdb.UserDao().insert(user);
                    if (user.getLastUpdate() > time) {
                        time=user.getLastUpdate();
                    }
                }
                User.setLocalLastUpdate(time);

            });

        });
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

    public void updateUser(User user, Listener<Void> listener) {
        firebaseModel.updateUser(user,listener);
        refreshAllUsers();
    }

    public void updatePost(Post post, Listener<Void> listener) {
        firebaseModel.updatePost(post,listener);
        getRefreshPosts();
    }


    public interface Listener<T> {
        void onComplete(T data);
    }

  /*  LiveData<Post> post;
    public LiveData<Post> getPostByUsername(String username) {
        post=localdb.postsDao().getPostByusername(username);
        return post;
    }*/

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

    public void deletePost(Post post, Listener<Void> listener) {
        firebaseModel.deletePost(post);
        executor.execute(()->{
            localdb.postsDao().delete(post);
            mainHandler.post(()->{listener.onComplete(null);});
        });
        getRefreshPosts();
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
        user=null;

    }

    public boolean isUserLog(){
        return firebaseModel.isUserLog();
    }

    public void loadUserData(String username, Model.Listener<User> listener) {
            firebaseModel.loadUserData(username, listener);
        }



    // Method to get all posts from local database
    public void getAllPostsFromLocalDb(Listener<List<Post>> listener) {
        executor.execute(() -> {
            List<Post> posts = localdb.postsDao().getAll();
            mainHandler.post(() -> {
                listener.onComplete(posts);
            });
        });
    }
    public void getPostByCity(String city,Listener<List<Post>> listener){
        getRefreshPosts();

        executor.execute(()->{
            StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Post WHERE 1=1 ");

            List<Object> args = new ArrayList<>();

            if (!Objects.equals(city,"city")) {
                queryBuilder.append(" AND city = ?");
                args.add(city);
            }

            SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryBuilder.toString(), args.toArray());
            List<Post> data= localdb.postsDao().getPostsByQuery(query);
            mainHandler.post(()->listener.onComplete(data));
        });

    }

  /*  public void getUserPosts(String username, GetUserPostsListener listener) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            List<Post> posts = localDb.getPostsByUsername(username);
            AppExecutors.getInstance().mainThread().execute(() -> listener.onComplete(posts));
        });*/
  public void getPostsByUsername(String username,Listener<List<Post>> listener){
      getRefreshPosts();

      executor.execute(()->{
          StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Post WHERE 1=1 ");

          List<Object> args = new ArrayList<>();

          if (!Objects.equals(username,"username")) {
              queryBuilder.append(" AND username = ?");
              args.add(username);
          }

          SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryBuilder.toString(), args.toArray());
          List<Post> data= localdb.postsDao().getPostsByQuery(query);
          mainHandler.post(()->listener.onComplete(data));
      });

  }

}