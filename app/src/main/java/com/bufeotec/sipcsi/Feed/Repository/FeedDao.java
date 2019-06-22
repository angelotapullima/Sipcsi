package com.bufeotec.sipcsi.Feed.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

//import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;

import com.bufeotec.sipcsi.Feed.Models.ModelFeed;

import java.util.List;

@Dao
public interface FeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ModelFeed modelFeed);

    @Query("SELECT * from feed ")
    LiveData<List<ModelFeed>> getAllPosts();

    @Query("DELETE FROM feed")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<ModelFeed> modelFeed);

}
