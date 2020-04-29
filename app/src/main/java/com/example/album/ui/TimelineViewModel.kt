package com.example.album.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.ImageData
import com.example.album.data.ImageRepository
import com.example.album.firebase.FirebaseSource

class TimelineViewModel : ViewModel() {
    var lvdata: MutableLiveData<List<ImageData>> = MutableLiveData<List<ImageData>>()
    var firebaseSource: FirebaseSource = FirebaseSource()
    var repo= ImageRepository(firebaseSource)

    fun getTimelineData(user:String):MutableLiveData<List<ImageData>>
    {
       var a= repo.getTimelineData(user)
        return  a
    }
}
