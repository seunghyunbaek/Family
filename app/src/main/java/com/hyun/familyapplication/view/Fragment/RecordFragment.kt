package com.hyun.familyapplication.view.Fragment


import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.view.Activity.WriteRecordActivity
import com.hyun.familyapplication.view.Adapter.RecordAdapter
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class RecordFragment : BaseFragment() {

    lateinit var db: DBHelper
    var lstRecrods: List<Record> = ArrayList<Record>()

    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_record, container, false)

//        db = DBHelper(view.context, TABLE_NAME)
        db = DBHelper(context!!, TABLE_NAME)


        val fab: View = view.findViewById(R.id.fab_record)
        fab.setOnClickListener {
            Intent(view.context, WriteRecordActivity::class.java).let {
                startActivity(it)
            }
        }

        mRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_record)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
        refreshData()

        getRecordsAsyncTask(view.context).execute("http://172.30.1.4:10381/elections/getrecords")

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    companion object {
        private val TABLE_NAME = "Record"


        class getRecordsAsyncTask internal constructor(context: Context) :
            AsyncTask<String, Any, String?>() {

            private val fragmentReference: WeakReference<Context> = WeakReference(context)

            override fun doInBackground(vararg params: String?): String? {
                val url = params[0]
                val mURL = URL(url)
                with(mURL.openConnection() as HttpURLConnection) {
                    requestMethod = "GET"
                    println("\nSending 'GET' request to URL : $url")
                    println("Response Code : $responseCode")
                    BufferedReader(InputStreamReader(inputStream)).use {
                        var response = it.readText()
                        println(response)
                        return response
                    }
                }
            }

            override fun onPostExecute(result: String?) {
                super.onPostExecute(result)
                val fragment = fragmentReference.get()
                Toast.makeText(fragment, result, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
