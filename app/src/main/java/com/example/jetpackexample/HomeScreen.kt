package com.example.jetpackexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class HomeScreen : AppCompatActivity(), View.OnClickListener {
        lateinit var background:Button
        lateinit var list:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        background=findViewById(R.id.background)
        list=findViewById(R.id.list)

        background.setOnClickListener(this)
        list.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if(v.id==R.id.background)
            {
                val intent=Intent(this,BackgroundActivity::class.java)
                startActivity(intent)

            }
            if(v.id==R.id.list)
            {
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)

            }
        }
    }
}
