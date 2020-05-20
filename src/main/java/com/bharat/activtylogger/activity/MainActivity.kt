package com.bharat.activtylogger.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bharat.activtylogger.R

class MainActivity : AppCompatActivity() {
    val TAG:String= MainActivity::class.java.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v(TAG,"MainActivity onCreate ")
    }


    override fun onStart() {
        super.onStart()

        Log.v(TAG,"MainActivity onStart ")
    }


    override fun onResume() {
        super.onResume()
        Log.v(TAG,"MainActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG,"MainActivity onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG,"MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG,"MainActivity onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.v(TAG,"MainActivity onRestart")
    }

    fun buttonClick(view: View) {

        startActivity(Intent(this, SecondActivity::class.java))
    }

    fun fragmentClick(view: View) {

        supportFragmentManager.beginTransaction().add(R.id.main,FirstFragment.newInstance()).addToBackStack(null).commit()
    }

}
