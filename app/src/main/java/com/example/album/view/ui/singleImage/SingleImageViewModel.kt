package com.example.album.view.ui.singleImage

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.model.data.pojo.ImageData
import com.example.album.model.data.repo.ImageRepository
import com.example.album.model.data.firebase.FirebaseSource

class SingleImageViewModel : ViewModel()
{

    var firebaseSource: FirebaseSource = FirebaseSource()
    var repo= ImageRepository(firebaseSource)

    fun deleteImage(data1: ImageData, activity:Activity): MutableLiveData<Boolean> {
       var data= repo.deleteImage(data1, activity!!.applicationContext)
        return data
    }
}