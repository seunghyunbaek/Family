package com.hyun.myapplication.view.Fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.hyun.myapplication.R
import com.hyun.myapplication.view.Adapter.PictureAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PicturesFragment : BaseFragment() {

    lateinit var pictureRecyclerView:RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pictures, container, false)

        pictureRecyclerView = view.findViewById(R.id.pictureRecyclerView)
        pictureRecyclerView.layoutManager = GridLayoutManager(view.context, 4)
        pictureRecyclerView.adapter = PictureAdapter()

        return view
    }

    override fun initPresenter() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
