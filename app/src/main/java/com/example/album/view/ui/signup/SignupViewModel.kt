package com.example.album.view.ui.signup

import android.app.Activity
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.model.data.repo.ImageRepository
import com.example.album.model.data.firebase.FirebaseSource

class SignupViewModel :ViewModel()
{
    var repository=
        ImageRepository(FirebaseSource())


    fun signup(name:String,email: String,password:String,imageUrl:String,activity:Activity): MutableLiveData<Boolean> {
        var data=repository.signUp(name,email,password,imageUrl,activity)
        return data
    }

    fun uploadImage(id:String,uri: Uri,activity: Activity): MutableLiveData<String> {
        var data=repository.uploadProfileImage(id,uri,activity)
        return data
    }

    fun updateProfileUrl(userid:String,imageUrl:String): MutableLiveData<Boolean> {
        var data=repository.uploadProfileUrl(userid,imageUrl)
        return data
    }
}