package com.bufeotec.sipcsi.Feed.Repository;

import android.os.AsyncTask;

public class FeedDbAsync extends AsyncTask<Void, Void, Void> {

    private final FeedDao mDao;

    FeedDbAsync(FeedRoomDataBase db) {
        mDao = db.postInfoDao();
    }

    @Override
    protected Void doInBackground(final Void... params) {
        //mDao.deleteAll(); //esto borra todo lo guardado
       /* User user = new User("Chandra","SW");
        mDao.insert(user);
        user = new User("Mohan","student");
        mDao.insert(user);*/
        return null;
    }
}
