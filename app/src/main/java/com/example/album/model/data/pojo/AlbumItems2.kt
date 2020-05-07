package com.example.album.model.data.pojo

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions





data class AlbumItems2(var name:String?,var imgUrl:String?) {

companion object{
 val IMAGETYPE2=2
 val IMAGETYPE4=4
 @JvmStatic @BindingAdapter("album1")
  fun loadImage(view: ImageView, imageUrl: String?) {
   Glide.with(view.context)
    .load(imageUrl)
    .into(view)
 }
}
}