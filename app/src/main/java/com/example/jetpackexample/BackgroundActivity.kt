package com.example.jetpackexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.example.jetpackexample.ViewModels.MainActivityColorGenerator

class BackgroundActivity : AppCompatActivity() {
    lateinit var button: Button
    lateinit var layout: ConstraintLayout
    var Tag=this.javaClass.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background)
        button=findViewById(R.id.button)
        layout=findViewById(R.id.mainLayout)
        val viewModel: MainActivityColorGenerator = ViewModelProviders.of(this).get(
            MainActivityColorGenerator::class.java)
        lifecycle.addObserver(viewModel)
        layout.setBackgroundColor(viewModel.getColor())
        button.setOnClickListener(View.OnClickListener {

            var color:Int= viewModel.generateColor()
            Log.v(Tag,"color code"+color)
            layout.setBackgroundColor(color)

        })
    }
}
