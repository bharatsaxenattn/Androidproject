package com.example.album.ui.timeline

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
        var viewModel: TimelineViewModel = ViewModelProviders.of(activity!!).get(
            TimelineViewModel::class.java)
        var user=FirebaseSource().getFirebaseUser(this.context!!)
        viewModel.getTimelineData(user).observe(activity!!, Observer<List<ImageData>> {
            shimmerFrameLayout.visibility = View.GONE
            shimmerFrameLayout.stopShimmer()
            var manager= GridLayoutManager(context,4)
               recyclerView.layoutManager=manager
               var adapter: ImageAdapter = ImageAdapter(it,activity!!.supportFragmentManager)
               recyclerView.adapter=adapter
        })

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(TimelineViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
