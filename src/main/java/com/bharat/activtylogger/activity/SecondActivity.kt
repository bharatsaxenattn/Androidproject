package com.bharat.activtylogger.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bharat.activtylogger.R

class SecondActivity : AppCompatActivity() {

    val TAG:String= SecondActivity::class.java.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        Log.v(TAG,"Second activity onCreate ")


    }


    override fun onStart() {
        super.onStart()

        Log.v(TAG,"SecondActivity onStart ")
    }


    override fun onResume() {
        super.onResume()
        Log.v(TAG,"SecondActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG,"SecondActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG,"SecondActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG,"SecondActivity onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.v(TAG,"SecondActivity onRestart")
    }

}
