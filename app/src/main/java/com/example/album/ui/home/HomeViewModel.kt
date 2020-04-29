package com.example.album.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.AlbumItems2
import com.example.album.ImageData
import com.example.album.data.ImageRepository
import com.example.album.firebase.FirebaseSource

class HomeViewModel : ViewModel() {

    var lvdata: MutableLiveData<List<ImageData>> = MutableLiveData<List<ImageData>>()
    var firebaseSource: FirebaseSource = FirebaseSource()
    var repo= ImageRepository(firebaseSource)

    fun getCategoryData(user:String):MutableLiveData<List<AlbumItems2>>
    {
        var a= repo.getCategoryImageData(user)
        return  a
    }
}