package com.hyun.myapplication.view.Fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.R
import com.hyun.myapplication.view.Activity.MainActivity
import com.hyun.myapplication.view.Activity.WriteRecordActivity
import com.hyun.myapplication.view.Adapter.RecordAdapter
import kotlinx.android.synthetic.main.fragment_record.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RecordFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_record, container, false)

        initPresenter()

        val fab: View = view.findViewById(R.id.recordFab)
        fab.setOnClickListener {
            Intent(view.context, WriteRecordActivity::class.java).let{
                startActivity(it)
            }
        }

        val mRecyclerView = view.findViewById<RecyclerView>(R.id.recordRecyclerView)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        mRecyclerView.adapter = RecordAdapter()

        return view
    }

    override fun initPresenter() {

    }

}
