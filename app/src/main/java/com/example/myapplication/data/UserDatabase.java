package com.example.myapplication.data;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.model.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();



    private static UserDatabase instance;


    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            UserDatabase.class, "user_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
