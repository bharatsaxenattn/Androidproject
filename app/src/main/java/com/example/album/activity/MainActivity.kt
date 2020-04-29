package com.example.album.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.album.R
import com.example.album.fragments.Login
import com.example.album.firebase.FirebaseSource

class MainActivity : AppCompatActivity() {
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseSource().getFirebaseUser(applicationContext)
        fragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.main_layout, Login.newInstance()).commit()


    }

    override fun onResume() {
        super.onResume()
        Log.v("OnResume","MainActivity")
        val count = supportFragmentManager.backStackEntryCount
        Log.v("MainActivityBackStaack",count.toString())
    }


}
