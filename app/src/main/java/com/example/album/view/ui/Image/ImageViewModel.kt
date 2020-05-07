package com.example.album.view.ui.Image

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.model.data.pojo.ImageData
import com.example.album.model.data.repo.ImageRepository
import com.example.album.model.data.firebase.FirebaseSource

class ImageViewModel: ViewModel() {

     var lvdata: MutableLiveData<List<ImageData>> =MutableLiveData<List<ImageData>>()
    var firebaseSource:FirebaseSource= FirebaseSource()
    var repo= ImageRepository(firebaseSource)


    fun getImageData(path:String,child:String,child1:String): MutableLiveData<List<ImageData>> {

        getData(path,child,child1);

        return lvdata;
    }

    private fun getData(path: String, child: String, child1: String) {

        lvdata= repo.getData(path,child,child1)
        //val list:MutableLiveData<List<ImageData>> = repo.getData(path,child,child1)

    }


    fun uploadImage(path: String, child: String, context: Context,uri: Uri){
        repo.uploadImage(path,child,context,uri)

    }

}