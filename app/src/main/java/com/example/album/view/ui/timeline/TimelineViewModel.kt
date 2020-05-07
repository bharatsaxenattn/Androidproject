package com.example.album.view.ui.timeline

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.model.data.pojo.ImageData
import com.example.album.model.data.repo.ImageRepository
import com.example.album.model.data.firebase.FirebaseSource

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
