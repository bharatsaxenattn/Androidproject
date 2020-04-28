package com.example.album.ui.Image

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.ImageData
import com.example.album.data.Imagerepository
import com.example.album.firebase.FirebaseSource

class ImageViewModel: ViewModel() {

     var lvdata: MutableLiveData<List<ImageData>> =MutableLiveData<List<ImageData>>()
    var firebaseSource:FirebaseSource= FirebaseSource()
    var repo=Imagerepository(firebaseSource)


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