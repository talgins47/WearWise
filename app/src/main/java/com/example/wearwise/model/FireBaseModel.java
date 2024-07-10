package com.example.wearwise.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.media3.common.util.UnstableApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.MemoryCacheSettings;
import com.google.firebase.firestore.MemoryLruGcSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
        mAuth = FirebaseAuth.getInstance();
    }

    public void getAllPostSince(Long since, Model.Listener<List<Post>> callback) {
        db.collection(Post.COLLECTION).whereGreaterThanOrEqualTo(Post.LAST_UPDATE, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<Post> list = new LinkedList<>();
                        if (task.isSuccessful()) {
                            QuerySnapshot jsonList = task.getResult();
                            for (DocumentSnapshot json : jsonList) {
                                Post pt = Post.fromJson(json.getData());
                                pt.id = json.getId();
                                list.add(pt);
                            }
                        }
                        callback.onComplete(list);
                    }
                });

    }

    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener) {
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
        db.collection("users").document(user.username).set(User.toJson(user));

    }

    public void logIn(String username, String password, Model.Listener<Boolean> listener) {
        db.collection("users").document(username).get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    User user = User.fromJson(document.getData());
                    mAuth.signInWithEmailAndPassword(user.getEmail(), password)
                            .addOnCompleteListener(authTask -> {
                                if (authTask.isSuccessful()) {
                                    listener.onComplete(true); // Login successful
                                } else {
                                    listener.onComplete(false); // Authentication failed
                                }
                            });
                } else {
                    listener.onComplete(false); // User document does not exist
                }
            } else {
                listener.onComplete(false); // Firestore document retrieval failed
            }
        });
    }

    public void addPost(Post post, Model.Listener<Void> postListener) {
       post.uploadPostTime = Calendar.getInstance().getTimeInMillis();
        db.collection("Posts").document().set(Post.toJson(post)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                postListener.onComplete(null);
            }
        });
    }

    public void createUser(User user, String password, Model.Listener<Boolean> listener) {
        addUser(user);
        mAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder().setDisplayName(user.username).build();
                        mAuth.getCurrentUser().updateProfile(profile);
                        Model.instance().username = user.username;
                        Model.instance().refreshAllUsers();
                        listener.onComplete(task.isSuccessful());

                    }
                });
    }

    public void isUserNameExist(String userName, Model.Listener<Boolean> listener) {
        db.collection("users").document(userName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                listener.onComplete(task.getResult().getData() != null);
            }
        });
    }

    public void isEmailExist(String email, Model.Listener<Boolean> listener) {
        db.collection("users").whereEqualTo("email", email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                User user = null;
                for (DocumentSnapshot document : task.getResult()) {
                    user = User.fromJson(document.getData());

                }
                listener.onComplete(user != null);
            }
        });
    }


    public void signOut() {
        mAuth.signOut();
    }

    public boolean isUserLog() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return currentUser != null;

    }

    public void loadUserData(String username, Model.Listener<User> listener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        db.collection("users").document(username).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User user = User.fromJson(document.getData());
                        listener.onComplete(user);

                    }
                }
            }
        });
    }

   /* public void updatePost(Post post, Model.Listener<Void> listener) {
        db.collection("posts").document(post.id).update(Post.toJson(post)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Model.instance().getRefreshPosts();
                listener.onComplete(null);
            }
        });
    }*/
    public String getLoggedUserUsername() {
        String username = mAuth.getCurrentUser().getDisplayName();
        return username;
    }

    public void getAllUsersSince(Long since, Model.Listener<List<User>> callback) {
        db.collection("users").whereGreaterThanOrEqualTo(User.LAST_UPDATE, new Timestamp(since, 0)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            List<User> data = new ArrayList<>();

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        data.add(User.fromJson(document.getData()));
                    }
                    callback.onComplete(data);
                }

            }
        });
    }


    public void getLoggedUser(Model.Listener<User> listener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.d("TAG", currentUser.getEmail());
        db.collection("users").whereEqualTo("email", currentUser.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                User user = User.fromJson(task.getResult().getDocuments().get(0).getData());

                listener.onComplete(user);
            }
        });
    }

    public void updateUser(User user, Model.Listener<Void> listener) {
        db.collection("users").document(user.username).update(User.toJson(user)).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Model.instance().refreshAllUsers();
                    listener.onComplete(null);

                }
            }
        });
    }


    public void updatePost(Post post, Model.Listener<Void> listener) {
        db.collection("Posts").document(post.username).update(Post.toJson(post)).addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Model.instance().refreshAllUsers();
                    listener.onComplete(null);

                }
            }
        });
    }
}
