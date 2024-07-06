package com.example.wearwise.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface PostsDao {

    @Query("SELECT * FROM Post WHERE city = :city ORDER BY lastUpdate DESC")
    List<Post> getPostByCity(String city);

    @Query("SELECT * FROM Post ORDER BY lastUpdate DESC")
    List<Post> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post posts);

    @Delete
    void delete(Post post);
}