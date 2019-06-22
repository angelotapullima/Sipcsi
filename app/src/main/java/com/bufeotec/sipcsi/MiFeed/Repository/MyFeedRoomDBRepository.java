package com.bufeotec.sipcsi.MiFeed.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.bufeotec.sipcsi.MiFeed.Models.ModelMyFeed;

import java.util.List;

//import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;

public class MyFeedRoomDBRepository {
    private MyFeedInfoDao myFeedInfoDao;
    LiveData<List<ModelMyFeed>> mAllPosts;

    public MyFeedRoomDBRepository(Application application){
         MyFeedRoomDataBase db = MyFeedRoomDataBase.getDatabase(application);
        myFeedInfoDao = db.postInfoDao();
        mAllPosts = myFeedInfoDao.getAllPosts();
     }

    public LiveData<List<ModelMyFeed>> getAllPosts() {
        return mAllPosts;
    }

    public void insertPosts (List<ModelMyFeed> modelMyFeed) {
        new insertAsyncTask(myFeedInfoDao).execute(modelMyFeed);
    }

    public void deleteAllPost() {
        new DeleteAllPostAsyncTask(myFeedInfoDao).execute();
    }



    public void deleteMovie(ModelMyFeed modelMyFeed) {
        new DeleteMovieAsyncTask(myFeedInfoDao).execute(modelMyFeed);
    }

    private static class insertAsyncTask extends AsyncTask<List<ModelMyFeed>, Void, Void> {

        private MyFeedInfoDao mAsyncTaskDao;

        insertAsyncTask(MyFeedInfoDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final List<ModelMyFeed>... params) {
            mAsyncTaskDao.insertPosts(params[0]);
            return null;
        }
    }


    private static class DeleteMovieAsyncTask extends AsyncTask<ModelMyFeed, Void, Void> {
        private MyFeedInfoDao myFeedInfoDao;

        private DeleteMovieAsyncTask(MyFeedInfoDao myFeedInfoDao) {
            this.myFeedInfoDao = myFeedInfoDao;
        }

        @Override
        protected Void doInBackground(ModelMyFeed... params) {
            myFeedInfoDao.deleteMovie(params[0]);
            return null;
        }
    }

    private static class DeleteAllPostAsyncTask extends AsyncTask<Void, Void, Void> {
        private MyFeedInfoDao myFeedInfoDao;

        private DeleteAllPostAsyncTask(MyFeedInfoDao myFeedInfoDao) {
            this.myFeedInfoDao = myFeedInfoDao ;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myFeedInfoDao.deleteAll();
            return null;
        }
    }
}
