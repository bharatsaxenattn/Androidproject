package com.example.album.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.album.adapters.ImageAdapter
import com.example.album.ImageData

import com.example.album.R
import com.example.album.firebase.FirebaseSource
import com.facebook.shimmer.ShimmerFrameLayout

class TimelineFragment : Fragment() {
    lateinit var shimmerFrameLayout: ShimmerFrameLayout

    lateinit var recyclerView: RecyclerView
    companion object {
        fun newInstance() = TimelineFragment()
    }

    private lateinit var viewModel: TimelineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view=inflater.inflate(R.layout.timeline_fragment, container, false)
        shimmerFrameLayout=view.findViewById(R.id.parentShimmerLayout)
        shimmerFrameLayout.startShimmer()
        recyclerView=view.findViewById(R.id.timeline_recycler)
        getTimelineData()
        return view
    }

    private fun getTimelineData() {
        var viewModel: TimelineViewModel = ViewModelProviders.of(activity!!).get(TimelineViewModel::class.java)
        var user=FirebaseSource().getFirebaseUser(this.context!!)
        viewModel.getTimelineData(user).observe(activity!!, Observer<List<ImageData>> {
            shimmerFrameLayout.visibility = View.GONE
            shimmerFrameLayout.stopShimmer()
            var manager= GridLayoutManager(context,4)
               recyclerView.layoutManager=manager
               var adapter: ImageAdapter = ImageAdapter(it,activity!!.supportFragmentManager)
               recyclerView.adapter=adapter
        })
        /*var arrayList: ArrayList<ImageData>
        arrayList=ArrayList()
        var storage=FirebaseDatabase.getInstance()
        var a=FirebaseSource().getFirebaseUser(activity!!.applicationContext)
        var ref=storage.getReference("UserImages").child(a)
        ref.addValueEventListener(object: ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot) {

                if(p0.getValue()!=null) {
                    //Log.v("value123",p0.getValue().toString())
                    var hashMap:HashMap<String,HashMap<String,HashMap<String,String>>> = p0.getValue()
                            as HashMap<String, HashMap<String, HashMap<String, String>>>

                    for(key in hashMap.keys)
                    {
                        Log.v("items",hashMap[key].toString())
                        var a=hashMap[key]

                        for (key1 in a!!.keys)
                        {
                         var data1=a[key1]
                            Log.v("data123",data1.toString())
                            var title = data1!!["title"]
                            var time = data1["time"]
                            var url = data1["imageUrl"]
                            var data = ImageData(url, title, time,key1)
                            arrayList.add(data)
                        }

                    }

                }
                var manager=GridLayoutManager(activity,4)
                recyclerView.layoutManager=manager

                arrayList.sortByDescending({selector(it)})
                //Log.v("inside123",arrayList[0].toString())
                var adapter: ImageAdapter = ImageAdapter(arrayList,activity!!.supportFragmentManager)
                recyclerView.adapter=adapter

            }

            override fun onCancelled(p0: DatabaseError) {
                //Toast.makeText(activity,"Error occur!!", Toast.LENGTH_LONG).show()
            }
        })

        return arrayList*/
    }

    private fun selector(it: ImageData):String {

        return it.time!!

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimelineViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
