package com.hyun.myapplication.view.Fragment


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.myapplication.DBHelper.RecordDBHelper
import com.hyun.myapplication.R
import com.hyun.myapplication.model.Record
import com.hyun.myapplication.view.Activity.WriteRecordActivity
import com.hyun.myapplication.view.Adapter.RecordAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RecordFragment : BaseFragment() {

    lateinit var db:RecordDBHelper
    var lstRecrods:List<Record> = ArrayList<Record>()

    lateinit var mRecyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_record, container, false)

        db = RecordDBHelper(view.context)


        val fab: View = view.findViewById(R.id.fab_record)
        fab.setOnClickListener {
            Intent(view.context, WriteRecordActivity::class.java).let{
                startActivity(it)
            }
        }

        mRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_record)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        refreshData()

        return view
    }

    fun refreshData() {
        lstRecrods = db.allRecord
        val adapter = RecordAdapter(context, lstRecrods)
        mRecyclerView.adapter = adapter
    }

    override fun initPresenter() {

    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }
}
