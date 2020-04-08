package com.example.jetpackexample.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.jetpackexample.DataItems


@Dao
public interface MyDao{
    @Insert
    public fun addUser(user: DataItems)

    @Query("Select * from User")
    public fun getUser(): LiveData<List<DataItems>>

}