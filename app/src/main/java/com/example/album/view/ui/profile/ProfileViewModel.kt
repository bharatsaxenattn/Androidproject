package com.example.album.view.ui.profile


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.album.model.data.repo.ImageRepository
import com.example.album.model.data.firebase.FirebaseSource

class ProfileViewModel :ViewModel()
{
    var repository=
        ImageRepository(FirebaseSource())


    fun profileImageUrl(user_id:String): MutableLiveData<String> {
        var data=repository.getProfileImageUrl(user_id)
        return data
    }
}