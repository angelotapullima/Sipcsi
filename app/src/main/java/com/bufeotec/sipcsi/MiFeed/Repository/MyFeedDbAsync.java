package com.bufeotec.sipcsi.MiFeed.Repository;

import android.os.AsyncTask;

public class MyFeedDbAsync extends AsyncTask<Void, Void, Void> {

    private final MyFeedInfoDao mDao;

    MyFeedDbAsync(MyFeedRoomDataBase db) {
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
