package com.example.jetpackexample.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jetpackexample.DataItems

@Database(entities = [DataItems::class], version = 1)
abstract class MyDatabase : RoomDatabase()
{
    abstract fun myDao(): MyDao?


    companion object {
        private var instance: MyDatabase? = null//single instance as we want to use only one instance of db  in our app

        fun getInstance(context: Context): MyDatabase? {
            if (instance == null) {
                synchronized(MyDatabase::class) {
                    instance = Room.databaseBuilder(context.applicationContext, MyDatabase::class.java, "my_user_db").build()
                }
            }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }



        }
    }
