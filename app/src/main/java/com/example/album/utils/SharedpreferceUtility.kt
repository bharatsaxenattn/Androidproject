package com.example.album.utils

import android.content.Context
import android.content.SharedPreferences

class SharedpreferceUtility(){
    companion object{
       lateinit var  sharedPreferences:SharedPreferences
        lateinit var editor:SharedPreferences.Editor
        fun sharedPreferece( file:String,context: Context):SharedPreferences
        {
             sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE)

            return sharedPreferences
        }

        fun sharedEditor(sharedPreferences:SharedPreferences):SharedPreferences.Editor
        {
            editor= sharedPreferences.edit()
            return editor
        }
        private fun putInt(key:String,value:Int)
        {
           editor.putInt(key,value).apply()
        }
        private fun putString(key:String,value:String)
        {
            editor.putString(key,value).apply()
        }
        private fun putBoolean(key:String,value:Boolean)
        {
            editor.putBoolean(key,value).apply()
        }


        private fun getInt(key:String):Int
        {
           var a:Int= sharedPreferences.getInt(key,0)
            return a
        }
        private fun getString(key:String):String
        {
            var a:String= sharedPreferences.getString(key,"")!!
            return a
        }
        private fun getBoolean(key:String,value:Int):Boolean
        {
            var a= sharedPreferences.getBoolean(key,false)
            return a
        }
    }
}