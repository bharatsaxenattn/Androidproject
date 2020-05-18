package com.bharat.retrofitpractice.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bharat.retrofitpractice.R
import com.bharat.retrofitpractice.adapter.MyAdapter

class StateListActivity : AppCompatActivity() {

    lateinit var list:ArrayList<String>
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_list)
        recyclerView=findViewById(R.id.recyclerView)

        list= ArrayList()
        insertStateName()
        recyclerViewTasks()





    }

    private fun recyclerViewTasks() {
        recyclerView.layoutManager=LinearLayoutManager(this)
        var adapter:MyAdapter= MyAdapter(list,this)
        recyclerView.adapter=adapter
        adapter.notifyDataSetChanged()
    }

    private fun insertStateName() {
        list.add("Andhra Pradesh")
        list.add("Arunachal Pradesh")
        list.add("Assam")
        list.add("Bihar")
        list.add("Chandigarh")
        list.add("Chhattisgarh")
        list.add("Delhi")
        list.add("Goa")
        list.add("Gujarat")
        list.add("Himachal Pradesh")
        list.add("Jharkhand")
        list.add("Karnataka")
        list.add("Ladakh")
        list.add("Lakshadweep")
        list.add("Madhya Pradesh")
        list.add("Maharashtra")
        list.add("Manipur")
        list.add("Meghalaya")
        list.add("Mizoram")
        list.add("Nagaland")
        list.add("Odisha")
        list.add("Rajasthan")
        list.add("Sikkim")
        list.add("Tamil Nadu")
        list.add("Uttarakhand")
        list.add("West Bengal")
    }
}
