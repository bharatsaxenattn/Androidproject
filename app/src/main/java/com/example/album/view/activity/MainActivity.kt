package com.example.album.view.activity

import android.content.Intent
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
        Log.v("onvack","exe")

        val count = supportFragmentManager.backStackEntryCount
        if(count==0)
        {
            super.onBackPressed()
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
        }
        else{
            supportFragmentManager.popBackStack()
        }



    }


}
