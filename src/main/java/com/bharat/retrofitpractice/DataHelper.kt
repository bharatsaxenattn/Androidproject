package com.bharat.retrofitpractice

import com.bharat.retrofitpractice.model.DataModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataHelper {


    //?q=ghaziabad&appid=7dd4dddd82eaffcb95229e73a2d4ada7
   @GET("weather")
    fun getStateWiseJsonObjectData(@Query("q")name:String,@Query("appid")key:String): Call<DataModel>

}