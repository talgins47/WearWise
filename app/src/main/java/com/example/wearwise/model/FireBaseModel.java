package com.example.wearwise.model;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.wearwise.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.MemoryLruGcSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FireBaseModel {
    private FirebaseFirestore db;
    private FirebaseStorage storage;
    private FirebaseAuth mAuth;

    FireBaseModel() {
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        MemoryCacheSettings memoryCacheSettings = MemoryCacheSettings.newBuilder()
                .setGcSettings(MemoryLruGcSettings.newBuilder()
                        .setSizeBytes(0)
                        .build())
                .build();

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setLocalCacheSettings(memoryCacheSettings)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void getAllPostSince(Long since, Model.Listener<List<Post>> callback) {
        db.collection(Post.COLLECTION).whereGreaterThanOrEqualTo(Post.LAST_UPDATE, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
    public void addPost(Post post, Model.Listener<Void> postListener) {
        Map<String, Object> postJson = new HashMap<>();
        postJson.put("postPicPath", post.getPostPicPath());
        postJson.put("city", post.getCity());
        postJson.put("describe", post.getDescribe());
        postJson.put("degree", post.getDegree());

        db.collection("Posts").document().set(post.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                postListener.onComplete(null);
            }
        });

    }

    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener){
        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("image/" + name + ".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());

                    }
                });

            }
        });
    }

    public void addUser(User user) {
        Map<String, Object> userJson = new HashMap<>();
        userJson.put("email", user.getEmail());
        userJson.put("full_name", user.getFullName());
        userJson.put("user_name", user.getUsername());
        userJson.put("password", user.getPassword());
        userJson.put("city", user.getCity());
        db.collection("users").document().set(user.toJson());

    }
    public void logIn(String username, String password, Model.Listener<Boolean> listener) {
        db.collection("Users").document(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                User user = User.fromJson(task.getResult().getData());
                mAuth.signInWithEmailAndPassword(user.email, password)
                        .addOnCompleteListener(task1 -> listener.onComplete(task1.isSuccessful()));
            } else {
                listener.onComplete(task.isSuccessful());
            }
        });
    }

    public void createUser(User user, Model.Listener<Boolean> listener) {
        addUser(user);
        mAuth.createUserWithEmailAndPassword(user.email,user.password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        listener.onComplete(true);
                        }
                });
    }

    public void isUserNameExist(String userName, Model.Listener<Boolean> listener) {
        db.collection("users").document(userName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               User user= User.fromJson(task.getResult().getData());
                   listener.onComplete(user!=null);
            }
        });
    }

    public void isEmailExist(String email, Model.Listener<Boolean> listener) {
        db.collection("users").document(email).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = User.fromJson(task.getResult().getData());
                listener.onComplete(user!=null);
            }
        });
    }
}

