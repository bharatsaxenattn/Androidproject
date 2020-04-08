package com.example.jetpackexample

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

class MyFragment : Fragment() {

    lateinit var name: TextView
    lateinit var address: TextView
    lateinit var email: TextView
    lateinit var mobile: TextView
    lateinit var submit: Button




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_my, container, false)
      //  newInstance()

        if (view != null) {
            name = view.findViewById(R.id.edtName)
            address = view.findViewById(R.id.edtAddress)
            email = view.findViewById(R.id.edtEmail)
            mobile = view.findViewById(R.id.edtMobile)
            submit = view.findViewById(R.id.submit)

         //  MainActivity.manager.beginTransaction().replace(R.id.mainLayout,MyFragment.newInstance()).addToBackStack(null).commit()
            submit.setOnClickListener(View.OnClickListener {
                var data: DataItems =
                    DataItems(
                        name.text.toString(), address.text.toString()
                        , email.text.toString(), mobile.text.toString()
                    )
                AddSyncTask(this).execute(data)
            })
        }
        return view
    }


    companion object {

        @JvmStatic
        fun newInstance(): MyFragment {

            var fragment: MyFragment = MyFragment()
            return fragment
        }

        class AddSyncTask internal constructor(context: MyFragment) :
            AsyncTask<DataItems, Void, Void>() {
            private val activityReference:WeakReference<MyFragment> =WeakReference(context)
            override fun onPreExecute() {
                super.onPreExecute()
            }


            override fun doInBackground(vararg params: DataItems?): Void? {
                var data: DataItems? = params[0]
                if (data != null) {

                    MainActivity.roomDatabase.myDao()?.addUser(data)
                }
                return null
            }

            @SuppressLint("WrongConstant")
            override fun onPostExecute(result: Void?) {
                Log.v("test", "added sucess")
                val c: Context? = activityReference.get()?.context
                val intent = Intent(c, MainActivity::class.java)

                if (c != null) {
                    Toast.makeText(c, "user deatil added sucessfully", Toast.LENGTH_LONG).show()
                    c.startActivity(intent)
                }




            }


        }
    }
}
