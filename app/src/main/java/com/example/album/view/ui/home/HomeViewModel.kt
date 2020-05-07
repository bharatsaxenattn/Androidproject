package com.example.album.view.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.model.data.pojo.AlbumItems2
import com.example.album.model.data.pojo.ImageData
import com.example.album.model.data.repo.ImageRepository
import com.example.album.model.data.firebase.FirebaseSource

class HomeViewModel : ViewModel() {

    var lvdata: MutableLiveData<List<ImageData>> = MutableLiveData<List<ImageData>>()
    var firebaseSource: FirebaseSource = FirebaseSource()
    var repo= ImageRepository(firebaseSource)

    fun getCategoryData(user:String):MutableLiveData<List<AlbumItems2>>
    {
        var categoryList= repo.getCategoryImageData(user)
        return  categoryList
    }
}