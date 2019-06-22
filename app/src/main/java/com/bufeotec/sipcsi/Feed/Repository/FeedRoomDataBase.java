package com.bufeotec.sipcsi.Feed.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bufeotec.sipcsi.Feed.Models.ModelFeed;

//import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;

@Database(entities = {ModelFeed.class}, version = 1)
public abstract class FeedRoomDataBase extends RoomDatabase {
    public abstract FeedDao postInfoDao();

    private static FeedRoomDataBase INSTANCE;


    static FeedRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (FeedRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            FeedRoomDataBase.class, "feed_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    private static Callback sRoomDatabaseCallback =
            new Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new FeedDbAsync(INSTANCE).execute();
                }
            };
}
