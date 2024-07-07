package com.example.wearwise.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wearwise.MyApplication;

@Database(entities = {Post.class, User.class}, version = 20)
abstract class AppLocalDbRepository extends RoomDatabase{
    public abstract PostsDao postsDao();
    public abstract UserDao UserDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class, "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb() {
    }
}