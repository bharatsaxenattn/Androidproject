package com.example.roomdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = Employee.class,version = 1)
public  abstract  class MyRoomDatabase extends RoomDatabase {

    public abstract MyDao myDao();
}
