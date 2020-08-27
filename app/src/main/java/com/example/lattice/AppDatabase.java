package com.example.lattice;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    private static AppDatabase DB;
    private static String db_name = "user";

    public static synchronized AppDatabase getInstance(Context context) {
        if (null == DB) {
            DB = Room.databaseBuilder(context.getApplicationContext() ,
                    AppDatabase.class , db_name).fallbackToDestructiveMigration().build();
        }
        return DB;
    }

    public void cleanUp(){
        DB = null;
    }
}

