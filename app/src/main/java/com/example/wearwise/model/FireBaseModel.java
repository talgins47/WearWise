package com.example.wearwise.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FireBaseModel {
    FirebaseFirestore db;

    FireBaseModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false).build();
    }

    public void getPostByCity(Model.GetPostByCity callback, String city) {
        db.collection("posts").whereEqualTo("city",city).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Post> list = new LinkedList<>();
                if(task.isSuccessful()){
                    QuerySnapshot jsonList = task.getResult();
                    for(DocumentSnapshot json: jsonList){
                       Post pt = Post.fromJson(json.getData());
                       list.add(pt);
                    }
                }
                callback.onComplete(list);
            }
        });

    }
    public void addPost(Post post, Model.AddPostListener postListener) {
        Map<String, Object> json = new HashMap<>();
        json.put("postPic", post.getPostPicPath());
        json.put("city", post.getCity());
        json.put("describe", post.getDescribe());
        json.put("degree", post.getDegree());

        db.collection("Posts").document().set(post.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                postListener.onComplete();
            }
        });

    }

}
