package com.example.album.view.ui.addcategory

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.model.data.repo.ImageRepository
import com.example.album.model.data.firebase.FirebaseSource

class AddCategoryViewModel :ViewModel()
{
    var repository=
        ImageRepository(FirebaseSource())


    fun addCategory(uri: Uri,context:Context,titletxt:String): MutableLiveData<Boolean> {
        var data=repository.uploadCategoryImage(uri,context!!,titletxt)
        return data
    }
}