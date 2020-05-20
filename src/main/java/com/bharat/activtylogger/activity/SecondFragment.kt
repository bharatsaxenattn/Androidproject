package com.bharat.activtylogger.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.bharat.activtylogger.R

class SecondFragment : Fragment() {
    val TAG:String= SecondFragment::class.java.name
    companion object {

        @JvmStatic
        fun newInstance():SecondFragment{
            return SecondFragment()
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.v(TAG,"SecondFragment onAttach ")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG,"SecondFragment onCreate ")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG,"SecondFragment onCreateView ")
        val view:View=inflater.inflate(R.layout.fragment_second, container, false)

        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG,"SecondFragment onActivityCreated ")
    }

    override fun onStart() {
        super.onStart()

        Log.v(TAG,"SecondFragment onStart ")
    }


    override fun onResume() {
        super.onResume()
        Log.v(TAG,"SecondFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG,"SecondFragment onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG,"SecondFragment onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v(TAG,"SecondFragment onDestroyView")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG,"SecondFragment onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.v(TAG,"SecondFragment onDetach ")
    }



}
