package com.example.wearwise.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.wearwise.MyApplication;

@Database(entities = {Post.class}, version = 2)
abstract class AppLocalDbRepository extends RoomDatabase{
    public abstract PostsDao postsDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class, "dbFileName.db")
                .fallbackToDestructiveMigration().build();
    }
    private AppLocalDb(){}
}