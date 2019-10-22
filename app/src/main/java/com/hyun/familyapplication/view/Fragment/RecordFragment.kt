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
import com.hyun.familyapplication.contract.RecordContract
import com.hyun.familyapplication.model.APIUtils
import com.hyun.familyapplication.model.Record
import com.hyun.familyapplication.model.RecordImage
import com.hyun.familyapplication.view.Activity.WriteRecordActivity
import com.hyun.familyapplication.view.Adapter.RecordAdapter
import org.json.JSONArray
import org.json.JSONObject
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
class RecordFragment : BaseFragment(), RecordContract.onRecordListener {

    lateinit var db: DBHelper
    var lstRecrods: List<Record> = ArrayList<Record>()
    var lstImages: List<RecordImage> = ArrayList<RecordImage>()

    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_record, container, false)

        db = DBHelper(context!!)
//        db = DBHelper()

        val fab: View = view.findViewById(R.id.fab_record)
        fab.setOnClickListener {
            Intent(view.context, WriteRecordActivity::class.java).let {
                startActivity(it)
            }
        }

        mRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_record)
        mRecyclerView.layoutManager = LinearLayoutManager(view.context)
//        refreshData()
        getDatas()

//        getRecordsAsyncTask(view.context).execute("http://172.30.1.4:10381/elections/getrecords")
        return view
    }

    fun getDatas() {
        if(db.getUser()?.room != null) {
            val url = getString(R.string.url) + "record/?room=" + db.getUser()?.room
            APIUtils.getRecordAsyncTask(this@RecordFragment).execute(url)
        }
    }

    fun refreshData() {
        lstRecrods = db.allRecord
        lstImages = db.allImages
        val adapter = RecordAdapter(context, lstRecrods, lstImages)
        mRecyclerView.adapter = adapter
    }

    override fun initPresenter() {

    }

    override fun onResume() {
        super.onResume()
//        refreshData()
        getDatas()
    }

    override fun onSuccess(result:String) {
        val jsonArray:JSONArray = JSONArray(result)
        val len = jsonArray.length()
        var count = 0
        val lst:MutableList<Record> = ArrayList<Record>()
        while(count < len) {
            val jsonObject = jsonArray.getJSONObject(count)
            val record = Record()
            record.id = jsonObject.getInt("id")
            record.email = jsonObject.getString("email")
            record.name = jsonObject.getString("name")
            record.date = jsonObject.getString("date")
            record.content = jsonObject.getString("content")
            record.room = jsonObject.getInt("room")
            record.record_images = JSONArray(jsonObject.getString("record_images"))
            lst.add(record)

//            val imgJsonArray = JSONArray(jsonObject.getString("record_images"))
//            println("**********************************************************************************")
//            println(imgJsonArray)
//            println("**********************************************************************************")

            count++
        }
        lstRecrods = lst

        val url = getString(R.string.url) + "image/"
        APIUtils.getImageAsyncTask(this).execute(url)
    }

    override fun onEnd(result: String) {
        val jsonArray:JSONArray = JSONArray(result)
        val len = jsonArray.length()
        var count = 0
        val lst:MutableList<RecordImage> = ArrayList<RecordImage>()
        while(count < len) {
            val jsonObject = jsonArray.getJSONObject(count)
            val recordImage = RecordImage()
            recordImage.id = jsonObject.getInt("id")
            recordImage.record = jsonObject.getInt("record")
            recordImage.uri = jsonObject.getString("uri")
            lst.add(recordImage)
            count++
        }
        lstImages = lst

        val adapter = RecordAdapter(context, lstRecrods, lstImages)
        mRecyclerView.adapter = adapter
        println("-------------------------------------------")
        adapter.notifyDataSetChanged()
        println("adapter.notifyDataSetChanged()")
        println("-------------------------------------------")
    }

    override fun onFailed() {
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
