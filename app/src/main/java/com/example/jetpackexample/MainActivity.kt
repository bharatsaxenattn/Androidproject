package com.example.jetpackexample

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackexample.Adapter.MyAdapter
import com.example.jetpackexample.Database.MyDatabase
import com.example.jetpackexample.ViewModels.LiveDataViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var floatButton: FloatingActionButton
    lateinit var viewModel: LiveDataViewModel
    lateinit var manager: FragmentManager
    lateinit var recyclerView: RecyclerView

    /*declaring the companion object*/
    companion object {
        public lateinit var roomDatabase: MyDatabase
        var instance: MainActivity? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        floatButton = findViewById(R.id.floatingActionButton)
        recyclerView = findViewById(R.id.recyclerView)

        //creating instance of database
        roomDatabase = MyDatabase.getInstance(this)!!
        manager = supportFragmentManager

        /*creating view model for the live data*/
        viewModel = ViewModelProviders.of(this).get(LiveDataViewModel::class.java)
        /*getting the value of the list*/
        viewModel.getUserData().observe(this, Observer<List<DataItems>> {
            val adapter = MyAdapter(it)
            recyclerView.layoutManager = LinearLayoutManager(instance, LinearLayout.VERTICAL, false)
            recyclerView.adapter = adapter
        })



        floatButton.setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.floatingActionButton -> {
                    //replacing with the fragment
                    manager.beginTransaction().replace(R.id.mainLayout, MyFragment.newInstance())
                        .addToBackStack(null).commit()
                }
            }
        }
    }


    /*class MainAsynTask: AsyncTask<Void,Void,Void>()
    {
        public lateinit var arrayList: ArrayList<DataItems>

        override fun onPreExecute() {
            super.onPreExecute()
        }
        override fun doInBackground(vararg params: Void?): Void? {
            arrayList= ArrayList<DataItems>()
           var list: List<DataItems>? =roomDatabase.myDao()?.getUser()

            if (list != null) {
                for (data in  list) {
                    arrayList.add(data)
                }
            }
            return null
        }

        @SuppressLint("WrongConstant")
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            val adapter=MyAdapter(arrayList)
            recyclerView.layoutManager=LinearLayoutManager(instance,LinearLayout.VERTICAL,false)
            recyclerView.adapter=adapter
        }

    }*/
}
