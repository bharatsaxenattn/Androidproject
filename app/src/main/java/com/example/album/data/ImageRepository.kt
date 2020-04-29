package com.example.album.data

import android.content.Context
import android.net.Uri
import com.example.album.ImageData
import com.example.album.firebase.FirebaseSource

class ImageRepository(private  val  firebase:FirebaseSource) {
    /* this method is used to get the data of the category image from firebase */
    fun getData(referencePath:String,firebase_child1_path:String,firebase_child2_path:String)=firebase.retrieveImageData(referencePath,firebase_child1_path,firebase_child2_path)

    /* this was used to upload the image to the firebase server*/
    fun uploadImage(referencePath:String,firebase_child_path:String,context: Context,uri: Uri)=firebase.uploadImage(referencePath,firebase_child_path,context,uri)

    /* deteing the image from the storage and database*/
    fun deleteImage(data: ImageData, applicationContext: Context)=firebase.deleteImage(data,applicationContext)

    /* used to check that user already exists or not in authentication*/
    fun checkAuth(email:String,password:String,context:Context)=firebase.checkRegisteredEmailValidation(email,password,context)

    /* get all the image for the timeline*/
    fun getTimelineData(user:String)=firebase.getTimelineData(user)

    /* get the data of the category image*/
    fun getCategoryImageData(firebase_user_id:String)=firebase.getCategoryImageData(firebase_user_id)

    /* used to get the image url of the profile*/
    fun getProfileImageUrl(firebase_user_id:String)=firebase.getProfileUrl(firebase_user_id)
}