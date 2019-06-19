package com.bufeotec.sipcsi.RetrofitRoom.Repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

//import com.andr.mvvm.RetrofitRoom.Models.ResultModel;

import com.bufeotec.sipcsi.RetrofitRoom.Models.ResultModel;

import java.util.List;

@Dao
public interface PostInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ResultModel resultModel);

    @Query("SELECT * from post_info ")
    LiveData<List<ResultModel>> getAllPosts();

    @Query("DELETE FROM post_info")
    void deleteAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPosts(List<ResultModel> resultModel);

}
