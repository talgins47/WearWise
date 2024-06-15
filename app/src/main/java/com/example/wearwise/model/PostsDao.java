package com.example.wearwise.model;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface PostsDao {
    @Query("select * from Post where city = :city")
    List<Post> getPostByCity(String city);

    @Query("select * from Post" )
    List<Post> getPost();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post...posts);

    @Delete
    void delete(Post post);

}
