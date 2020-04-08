package com.example.jetpackexample.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.jetpackexample.DataItems
import com.example.jetpackexample.MainActivity

class LiveDataViewModel:ViewModel() {

    lateinit var lvdata: LiveData<List<DataItems>>

        fun getUserData():LiveData<List<DataItems>>
        {
           lvdata= MainActivity.roomDatabase.myDao()?.getUser()!!
            return lvdata
        }



}