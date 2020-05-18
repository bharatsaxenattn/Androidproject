package com.bharat.retrofitpractice.activity

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.bharat.retrofitpractice.DataHelper
import com.bharat.retrofitpractice.R
import com.bharat.retrofitpractice.RetrofitBuilder
import com.bharat.retrofitpractice.databinding.ActivityMain1Binding
import com.bharat.retrofitpractice.model.DataModel
import com.facebook.shimmer.ShimmerFrameLayout
import retrofit2.Response
import java.time.LocalDateTime

class WeatherActivity : AppCompatActivity() {

    lateinit var state:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        state=intent.getStringExtra("state")
        getListData()
    }



    private fun getListData() {
       val service:DataHelper= RetrofitBuilder.buildService(DataHelper::class.java)
        var binding:ActivityMain1Binding=DataBindingUtil.setContentView(this,R.layout.activity_main1)
        var key="7dd4dddd82eaffcb95229e73a2d4ada7"
        val request:retrofit2.Call<DataModel> = service.getStateWiseJsonObjectData(state,key)

        request.enqueue(object : retrofit2.Callback<DataModel> {
            override fun onFailure(call: retrofit2.Call<DataModel>, t: Throwable) {
                Log.v("exception",t.message)
            }

            override fun onResponse(
                call: retrofit2.Call<DataModel>,
                response: Response<DataModel>
            ) {
                var response:DataModel?=response.body()

                val code=response!!.cod
                val name=response!!.name
                val time=response!!.timezone

                Log.v("code"," code is"+code +"\n"+name+"\n"+time )
             //   Log.v("response"," response is"+response.main.temp+"  and" +response.main.temp_min+"  nad"+ response.main.temp_max)
                binding.mainData=response
                binding.weatherDesc=response.weather[0].list
                binding.weatherMain=response.main



            }

        })


    }
}
