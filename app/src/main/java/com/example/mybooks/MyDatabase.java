package com.example.mybooks;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
//the database class here i used singleton type cuz i only need to initialize one database and need to make sure that there is only one otherwise there will be data leak
@Database(entities = {Account.class},version = 1)
public abstract class MyDatabase extends RoomDatabase {
    private static MyDatabase instance ;
    public abstract AccountDao accountDao ();
    public static synchronized MyDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),MyDatabase.class,"account_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance ;
    }

    }

