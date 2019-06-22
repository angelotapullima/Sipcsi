package com.bufeotec.sipcsi.MiFeed.Repository;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.bufeotec.sipcsi.MiFeed.Models.ModelMyFeed;

//import com.andr.mvvm.RetrofitRoom.Models.ModelFeed;

@Database(entities = {ModelMyFeed.class}, version = 1)
public abstract class MyFeedRoomDataBase extends RoomDatabase {
    public abstract MyFeedInfoDao postInfoDao();

    private static MyFeedRoomDataBase INSTANCE;


    static MyFeedRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyFeedRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyFeedRoomDataBase.class, "myfeed_database")
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
                    new MyFeedDbAsync(INSTANCE).execute();
                }
            };
}
