package com.bharat.retrofitpractice.model

import android.os.Build
import java.time.LocalDateTime

data class DataModel( var coord:CordinatesModel?=null,var weather:ArrayList<WeatherModel>,var base:String,
                      var main:WeatherMainModel,var visibility:String,var wind:WindModel,var clouds:CloudsModel
                      ,var dt:String,var sys:SystemModel,var timezone:Int,var id:Int,var name:String,var cod:Int)
{

    fun getTime():String
    {
        var mtime:String=""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             mtime= LocalDateTime.now().toString()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return mtime
    }
}