package com.example.album.data

import android.content.Context
import android.net.Uri
import com.example.album.ImageData
import com.example.album.firebase.FirebaseSource

class Imagerepository(private  val  firebase:FirebaseSource) {

    fun getData(path:String,child:String,child1:String)=firebase.retrieveImageData(path,child,child1)

    fun uploadImage(path:String,child:String,context: Context,uri: Uri)=firebase.uploadImage(path,child,context,uri)
    fun deleteImage(data: ImageData, applicationContext: Context)=firebase.deleteImage(data,applicationContext)
    fun checkAuth(email:String,password:String,context:Context)=firebase.checkRegisteredEmailValidation(email,password,context)
    fun getTimelineData(user:String)=firebase.getTimelineData(user)
    fun getCategoryImageData(user:String)=firebase.getCategoryImageData(user)
    fun getProfileImageUrl(user:String)=firebase.getProfileUrl(user)
}