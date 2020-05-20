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

class FirstFragment : Fragment() {
    val TAG:String= FirstFragment::class.java.name



    companion object {

        @JvmStatic
        fun newInstance():FirstFragment{
        return FirstFragment()
            }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.v(TAG,"FirstFragment onAttach ")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v(TAG,"FirstFragment onCreate ")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.v(TAG,"FirstFragment onCreateView ")

        val view:View=inflater.inflate(R.layout.fragment_first, container, false)
        val btnAdd: Button =view.findViewById(R.id.add_first_frag_btn)
        val btnReplace: Button =view.findViewById(R.id.replace_first_frag_btn)
        btnAdd.setOnClickListener(View.OnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.add(R.id.main,SecondFragment.newInstance())
                ?.addToBackStack(null)?.commit()
        })

        btnReplace.setOnClickListener( {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.main,SecondFragment.newInstance())
                ?.addToBackStack(null)?.commit()
        })
        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.v(TAG,"FirstFragment onActivityCreated ")
    }

    override fun onStart() {
        super.onStart()

        Log.v(TAG,"FirstFragment onStart ")
    }


    override fun onResume() {
        super.onResume()
        Log.v(TAG,"FirstFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.v(TAG,"FirstFragment onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.v(TAG,"FirstFragment onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.v(TAG,"FirstFragment onDestroyView")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.v(TAG,"FirstFragment onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.v(TAG,"FirstFragment onDetach ")
    }



}
