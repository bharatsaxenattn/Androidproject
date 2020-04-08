package com.example.jetpackexample.ViewModels

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainActivityColorGenerator :ViewModel(),LifecycleObserver{

    private  val TAG=this.javaClass.toString();
    private  var colorCode:Int=0

    fun getColor():Int
    {
        if(colorCode==0)
            colorCode=generateColor()
       return colorCode
    }
     fun generateColor() :Int {
        var r= Random.nextInt(256)
        var random= Random
        Log.v("Random", random.nextInt().toString())
        colorCode =Color.argb(255,random.nextInt(0,256),random.nextInt(0,256),random.nextInt(0,256))
        return colorCode

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun logCreate() {
       Log.v(TAG,"Oncreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun logStart() {
        Log.v(TAG,"On Start")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun logResume() {
        Log.v(TAG,"On Resme")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun logPause() {
        Log.v(TAG,"On Pause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun logStop() {
        Log.v(TAG,"On Stop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun logDestroy() {
        Log.v(TAG,"On Destroy")
    }

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG,"On Cleared")
    }
}