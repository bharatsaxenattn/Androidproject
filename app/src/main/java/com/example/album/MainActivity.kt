package com.example.album

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.album.fragments.Login
import com.example.album.firebase.FirebaseSource

class MainActivity : AppCompatActivity() {
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseSource().getFirebaseUser(applicationContext)
        fragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.main_layout, Login.newInstance()).addToBackStack(null).commit()

    }
}
