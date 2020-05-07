package com.example.album.view.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.album.model.data.adapters.MyAdapter
import com.example.album.model.data.pojo.AlbumItems2
import com.example.album.view.ui.addcategory.AddCategoryDialog
import com.example.album.R
import com.example.album.model.data.firebase.FirebaseSource
import com.example.album.view.ui.timeline.TimelineFragment
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var arrayList: ArrayList<AlbumItems2>
    lateinit var database:FirebaseDatabase
    lateinit var ref:DatabaseReference
    lateinit var grid2: ImageView
    lateinit var grid4: ImageView
    lateinit var timeline: ImageView
    val flag=0
    lateinit var shimmerFrameLayout: ShimmerFrameLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        shimmerFrameLayout=view.findViewById(R.id.parentShimmerLayout)
        shimmerFrameLayout.startShimmer()
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        recyclerView=view.findViewById(R.id.item_reccycelr)
        grid2=view.findViewById(R.id.categoryBtn)
        grid4=view.findViewById(R.id.timelineView)

        val count = activity!!.supportFragmentManager.backStackEntryCount
       Log.v("backstackcount",count.toString())
        var fab:FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            var tran=activity!!.supportFragmentManager
            tran.beginTransaction().replace(R.id.main_2,
                AddCategoryDialog.newInstance()).commit()
            // createAlbumDialog()
        }
        grid4.setOnClickListener(this)
        grid2.setOnClickListener(this)
    //   timeline.setOnClickListener(this)
        if(flag==0)
        {
            getCategoryImageData(2)
        }
        else
        {
            getCategoryImageData(4)
        }

        return view
    }

    override fun onClick(v: View?) {
        /* if user pressed on the timeline button*/
        if(v!!.id==R.id.timelineView)
        {

            var tran=activity!!.supportFragmentManager
            tran.beginTransaction().replace(R.id.main_2,
                TimelineFragment.newInstance()).commit()
        }
        /* if user selects the grid view for home fragment*/
        if (v!!.id==R.id.categoryBtn)
        {
            getCategoryImageData(2)
        }
       /* if(v!!.id==R.id.timeline)
        {

        }*/
    }


    fun getCategoryImageData(grid:Int)
    {
        recyclerView.layoutManager= GridLayoutManager(activity,grid) as RecyclerView.LayoutManager?
        var viewModel: HomeViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
        var user=FirebaseSource().getFirebaseUser(this.context!!)
        viewModel.getCategoryData(user).observe(activity!!, Observer<List<AlbumItems2>> {
            if(it!=null)
            {

                val adapter = MyAdapter(it, grid, activity!!.supportFragmentManager)
                shimmerFrameLayout.visibility = View.GONE
                shimmerFrameLayout.stopShimmer()
                recyclerView.adapter = adapter
            }


        })


    }
}

