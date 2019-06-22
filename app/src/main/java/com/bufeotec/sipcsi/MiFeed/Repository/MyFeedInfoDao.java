package com.bufeotec.sipcsi.MiFeed.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.bufeotec.sipcsi.MiFeed.Models.ModelMyFeed;

import java.util.List;

//import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;

@Dao
public interface MyFeedInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ModelMyFeed modelMyFeed);

    @Query("SELECT * from myfeed_info ")
    LiveData<List<ModelMyFeed>> getAllPosts();

    @Query("DELETE FROM myfeed_info")
    void deleteAll();


    @Delete
    void deleteMovie(ModelMyFeed modelMyFeed);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<ModelMyFeed> modelMyFeed);

}
