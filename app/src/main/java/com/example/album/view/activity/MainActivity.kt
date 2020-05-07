package com.example.album.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.album.R
import com.example.album.view.ui.login.Login
import com.example.album.model.data.firebase.FirebaseSource

class MainActivity : AppCompatActivity() {
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseSource().getFirebaseUser(applicationContext)



    }

    override fun onResume() {
        super.onResume()
        fragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.main_layout, Login.newInstance()).commit()
        Log.v("OnResume","MainActivity")
        val count = supportFragmentManager.backStackEntryCount
        Log.v("MainActivityBackStaack",count.toString())
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        Log.v("Count==",count.toString())
        if (count == 0) {
            super.onBackPressed()
            finish()
            // System.exit(1)
        }
        else {
            supportFragmentManager.popBackStack()
        }
    }


}
