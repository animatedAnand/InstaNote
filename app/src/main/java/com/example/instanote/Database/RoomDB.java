package com.example.instanote.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.instanote.Models.NotesModel;

@Database(entities = NotesModel.class,exportSchema = false,version = 1)
public abstract class RoomDB extends RoomDatabase {
    public static RoomDB database;
    public static String database_name="InstaNoteApp";

    public synchronized static RoomDB getInstance(Context context)
    {
        if(database==null)
        {
            database= Room.databaseBuilder(context.getApplicationContext(),RoomDB.class
            ,database_name).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build();
        }
        return database;
    }
    public abstract MainDAO mainDAO();
}
