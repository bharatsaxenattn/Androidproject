package com.example.album.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.album.Adapters.ImageAdapter
import com.example.album.Adapters.MyAdapter
import com.example.album.AlbumItems2
import com.example.album.Fragments.AddCategoryDialog
import com.example.album.ImageData
import com.example.album.POJO.CategoryData
import com.example.album.POJO.ProfileData
import com.example.album.R
import com.example.album.firebase.FirebaseSource
import com.example.album.ui.Image.ImageViewModel
import com.example.album.ui.TimelineFragment
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*

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
        grid2=view.findViewById(R.id.grid2)
        grid4=view.findViewById(R.id.grid4)
     //   timeline=view.findViewById(R.id.timeline)

        var fab:FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            var tran=activity!!.supportFragmentManager
            tran.beginTransaction().add(R.id.main_2,AddCategoryDialog.newInstance()).commit()
            // createAlbumDialog()
        }
        grid4.setOnClickListener(this)
        grid2.setOnClickListener(this)
    //   timeline.setOnClickListener(this)
        if(flag==0)
        {
            getImage(2)
        }
        else
        {
            getImage(4)
        }



       /* homeViewModel.text.observe(viewLifecycleOwner, Observer {
            //textView.text = it
        })*/
        return view
    }

    override fun onClick(v: View?) {
        if(v!!.id==R.id.grid4)
        {

            var tran=activity!!.supportFragmentManager
            tran.beginTransaction().replace(R.id.main_2,TimelineFragment.newInstance()).addToBackStack(null).commit()
        }
        if (v!!.id==R.id.grid2)
        {
            getImage(2)
        }
       /* if(v!!.id==R.id.timeline)
        {

        }*/
    }


    fun getImage(grid:Int)
    {
        recyclerView.layoutManager= GridLayoutManager(activity,grid) as RecyclerView.LayoutManager?
        var viewModel: HomeViewModel = ViewModelProviders.of(activity!!).get(HomeViewModel::class.java)
        var user=FirebaseSource().getFirebaseUser(this.context!!)
        viewModel.getCategoryData(user).observe(activity!!, Observer<List<AlbumItems2>> {
            val adapter = MyAdapter(it, grid, activity!!.supportFragmentManager)
            shimmerFrameLayout.visibility = View.GONE
            shimmerFrameLayout.stopShimmer()
            recyclerView.adapter = adapter

        })

       /* arrayList=ArrayList();
        database= FirebaseDatabase.getInstance()
        var a=FirebaseSource().getFirebaseUser(activity!!.applicationContext)
        ref=database.getReference("AlbumCategory").child(a)
        ref.addValueEventListener(object: ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.getValue() != null) {
                    var hashMap: HashMap<String, HashMap<String, String>?>?
                    hashMap = p0.getValue() as HashMap<String, HashMap<String, String>?>
                    for (key in hashMap.keys) {
                        var a = hashMap[key]!!
                        Log.v("value1==", a.toString())
                        Log.v("key==", key.toString())
                        var title = a["title"]
                        var url = a["url"]
                        var data = AlbumItems2(title, url)
                        arrayList.add(data)
                        //   arrayList.add(a)
                    }
                    //   Log.v("size==", "is" + arrayList.size)
                    val adapter = MyAdapter(arrayList, grid, activity!!.supportFragmentManager)
                    recyclerView.adapter = adapter
                    //    Log.v("value==", hashMap.toString())


                }
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(activity,"Error occur!!", Toast.LENGTH_LONG).show()
            }
        })*/
    }
}

