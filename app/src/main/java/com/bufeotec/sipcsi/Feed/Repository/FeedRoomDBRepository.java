package com.bufeotec.sipcsi.Feed.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

//import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;

import com.bufeotec.sipcsi.Feed.Models.ModelFeed;

import java.util.List;

public class FeedRoomDBRepository {
    private FeedDao feedDao;
    LiveData<List<ModelFeed>> mAllPosts;

    public FeedRoomDBRepository(Application application){
         FeedRoomDataBase db = FeedRoomDataBase.getDatabase(application);
        feedDao = db.postInfoDao();
        mAllPosts = feedDao.getAllPosts();
     }

    public LiveData<List<ModelFeed>> getAllPosts() {
        return mAllPosts;
    }
    public void insertPosts (List<ModelFeed> modelFeed) {
        new insertAsyncTask(feedDao).execute(modelFeed);
    }

    private static class insertAsyncTask extends AsyncTask<List<ModelFeed>, Void, Void> {

        private FeedDao mAsyncTaskDao;

        insertAsyncTask(FeedDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<ModelFeed>... params) {
            mAsyncTaskDao.insertPosts(params[0]);
            return null;
        }
    }
}
