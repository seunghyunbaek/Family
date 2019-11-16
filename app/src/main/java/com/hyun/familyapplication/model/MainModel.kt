package com.hyun.familyapplication.model

import android.content.Context
import com.hyun.familyapplication.DBHelper.DBHelper
import com.hyun.familyapplication.R
import com.hyun.familyapplication.contract.MainContract
import org.json.JSONArray
import org.json.JSONObject

class MainModel {
    companion object {
        fun checkMessage(context: Context, listener: MainContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            val url = context.getString(R.string.url) + "messagereceive/" + user?.email + "/"

            APIUtils.getCheckMessageAsyncTask(listener).execute(url)
        }

        fun checkRecord(context: Context, listener: MainContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            if (user?.room == null || user?.room == 0) return

            val url = context.getString(R.string.url) + "recordcount/" + user?.email + "/"

            APIUtils.getRecordReceiveAsyncTask(context, listener).execute(url)
        }

        fun getRecord(context: Context, countresult: String, listener: MainContract.Listener) {
            val db = DBHelper(context)
            val user = db.getUser()
            val url = context.getString(R.string.url) + "record/" + user?.room + "/"

            APIUtils.getRecordMainAsyncTask(countresult, listener).execute(url)
        }

        fun getNewCount(count: String, record: String): Int {
            val cntobj = JSONObject(count)
            var last = cntobj.getInt("last")

            val recordarr = JSONArray(record)
            val recordobj = recordarr.getJSONObject(0)
            val id = recordobj.getInt("id")

            val result = id - last

            if (result < 1)
                return 0

            return result
        }

        fun getCount(result: String): Int {
            val json = JSONObject(result)
            val count = json.getInt("count")

            return count
        }
    }
}