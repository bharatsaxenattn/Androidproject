package com.example.album.view.ui.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.model.data.repo.ImageRepository
import com.example.album.model.data.firebase.FirebaseSource

class LoginViewModel :ViewModel()
{
    var repository=
        ImageRepository(FirebaseSource())


    fun login(email: String,password:String,context:Context): MutableLiveData<Boolean> {
        var data=repository.checkAuth(email,password,context)
        return data
    }

    fun updateProfileUrl(userid:String,imageUrl:String): MutableLiveData<Boolean> {
        var data=repository.uploadProfileUrl(userid,imageUrl)
        return data
    }
}