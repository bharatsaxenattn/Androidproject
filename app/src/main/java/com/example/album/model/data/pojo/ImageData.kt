package com.example.album.model.data.pojo

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.io.Serializable

data class ImageData(var imageUrl:String?,var title:String?,var time:String?,var key:String?) :
    Serializable {

    companion object{

        @JvmStatic @BindingAdapter("image1")
        fun loadImage(view: ImageView, imageUrl: String?) {
            Glide.with(view.context)
                .load(imageUrl)
                .into(view)
        }
    }
}